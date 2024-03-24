package S3_3sem.S3_3hw.annotations2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Когда используется в процессе выполнения
@Target(ElementType.TYPE) // Над чем ставится
public @interface Table {
    String name();
}
