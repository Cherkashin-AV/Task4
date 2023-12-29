package ru.vtb.javaCourse.Task4.Converters;

import java.util.List;

public interface Convertable <T,E> {
         List<E> covert(List<T> source);

         Convertable getNext();

        default boolean hasNext(){
            return getNext() != null;
        }

        Class getRealClass();

    }
