package org.fenixsoft.jvm.chapter10;

import static javax.lang.model.element.ElementKind.ENUM_CONSTANT;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.ElementKind.INTERFACE;
import static javax.lang.model.element.ElementKind.METHOD;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.tools.Diagnostic.Kind.WARNING;

import java.util.EnumSet;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner14;;

/**
 * 프로그램 명명 규칙 검사용 컴파일러 플러그인: <br>
 * 프로그램에서 사용한 이름이 표준에서 어긋나면 컴파일러 경고(WARNING) 메시지 출력
 */
public class NameChecker {
    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    NameChecker(ProcessingEnvironment processsingEnv) {
        this.messager = processsingEnv.getMessager();
    }

    /**
     * 자바 프로그램의 이름을 검사한다.
     * 《자바 언어 명세》 6.1절에 따르면 자바 코드에서 이름은 다음 형식을 따라야 한다.
     *
     * <ul>
     * <li>클래스와 인터페이스: 대문자로 시작하는 낙타 명명법을 따른다.
     * <li>메서드: 소문자로 시작하는 낙타 명명법을 따른다.
     * <li>필드:
     * <ul>
     * <li>클래스 변수 혹은 인스턴스 변수: 소문자로 시작하는 낙타 명명법을 따른다.
     * <li>상수: 모두 대문자 혹은 밑줄
     * </ul>
     * </ul>
     */
    public void checkNames(Element element) {
        nameCheckScanner.scan(element);
    }

    /**
     * 이름 검사기 구현 클래스. JDK 14에서 새로 제공하는 ElementScanner14를 상속한다.
     * (JDK 15, 16, 17용 스캐너는 따로 제공되지 않음)
     * 방문자(visitor) 모드에서 추상 구문 트리의 원소에 접근한다.
     */
    private class NameCheckScanner extends ElementScanner14<Void, Void> {

        /**
         * 클래스 이름을 검사한다.
         */
        @Override
        public Void visitType(TypeElement e, Void p) {
            scan(e.getTypeParameters(), p);
            checkCamelCase(e, true);
            super.visitType(e, p);
            return null;
        }

        /**
         * 메서드 이름을 검사한다.
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void p) {
            if (e.getKind() == METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName()))
                    messager.printMessage(WARNING, "생성자와 혼동되지 않도록 일반 메서드 '" + name + "'은(는) 클래스 이름을 그대로 사용하면 안 된다.", e);
                checkCamelCase(e, false);
            }
            super.visitExecutable(e, p);
            return null;
        }

        /**
         * 변수 이름을 검사한다.
         */
        @Override
        public Void visitVariable(VariableElement e, Void p) {
            // 현재 변수가 열거형이나 상수라면 모두 대문자인지 확인하고,
            // 그 외에는 낙타 명명법에 부합하는지 확인한다.
            if (e.getKind() == ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e))
                checkAllCaps(e);
            else
                checkCamelCase(e, false);
            return null;
        }

        /**
         * 변수가 상수인지 검사한다.
         */
        private boolean heuristicallyConstant(VariableElement e) {
            if (e.getEnclosingElement().getKind() == INTERFACE)
                return true;
            else if (e.getKind() == FIELD && e.getModifiers().containsAll(EnumSet.of(PUBLIC, STATIC, FINAL)))
                return true;
            else {
                return false;
            }
        }

        /**
         * 입력 Element가 낙타 명명법을 따르는지 확인하여,
         * 따르지 않으면 경고 메시지를 출력한다.
         */
        private void checkCamelCase(Element e, boolean initialCaps) {
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            if (Character.isUpperCase(firstCodePoint)) {
                previousUpper = true;
                if (!initialCaps) {
                    messager.printMessage(WARNING, "이름 '" + name + "'은(는) 소문자로 시작해야 한다.", e);
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    messager.printMessage(WARNING, "이름 '" + name + "'은(는) 대문자로 시작해야 한다.", e);
                    return;
                }
            } else
                conventional = false;

            if (conventional) {
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previousUpper) {
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    } else
                        previousUpper = false;
                }
            }

            if (!conventional)
                messager.printMessage(WARNING, "이름 '" + name + "'은(는) 낙타 명명법(Camel Case Names)을 따라야 한다.", e);
        }

        /**
         * 모두 대문자인지 검사한다.
         */
        private void checkAllCaps(Element e) {
            String name = e.getSimpleName().toString();

            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            if (!Character.isUpperCase(firstCodePoint))
                conventional = false;
            else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int) '_') {
                        if (previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
            }

            if (!conventional)
                messager.printMessage(WARNING, "상수 '" + name + "'은(는) 대문자와 밑줄만 사용해야 하며, 문자로 시작해야 한다.", e);
        }
    }
}