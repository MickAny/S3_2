package tests.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Когда используется в процессе выполнения
@Target(ElementType.METHOD) // Над чем ставится
public @interface AfterAll {
}
