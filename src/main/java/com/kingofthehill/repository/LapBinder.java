package com.kingofthehill.repository;

import com.kingofthehill.repository.model.Lap;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(LapBinder.LapBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.PARAMETER})
public @interface LapBinder {

    class LapBinderFactory implements BinderFactory {
        public Binder build(Annotation annotation) {
            return (Binder<LapBinder, Lap>) (q, bind, lap) -> {
                q.bind("transponder", lap.getTransponder())
                        .bind("lapnr", lap.getLapNr())
                        .bind("laptime", lap.getLapTime());
            };
        }
    }
}