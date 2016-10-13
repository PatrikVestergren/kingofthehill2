package com.kingofthehill.repository;

import com.kingofthehill.repository.model.MinutesEntity;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

@BindingAnnotation(MinuteBinder.MinuteBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface MinuteBinder {

    class MinuteBinderFactory implements BinderFactory {
        public Binder build(Annotation annotation) {
            return (Binder<MinuteBinder, MinutesEntity>) (q, bind, m) -> {
                Array array = null;
                try {
                    array = q.getContext().getConnection().createArrayOf("integer", m.getLaps().toArray());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                q.bind("transponder", m.getTransponder())
                        .bind("bestminutesid", m.getId())
                        .bind("nroflaps", m.getNrOfLaps())
                        .bind("totaltime", m.getTotalTime())
                        .bind("modtime", m.getTime() != null ? m.getTime() : Timestamp.valueOf(LocalDateTime.now()))
                        .bindBySqlType("lapids", array, Types.ARRAY);

            };
        }
    }
}