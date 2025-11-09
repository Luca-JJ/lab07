package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    public enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

         Month(final int days) {
            this.days = days;
        }

        public static Month fromString(String str){
            Objects.requireNonNull(str);
            str = str.toUpperCase();
            Month found = null;
            for(Month month : Month.values()){
                if(month.name().startsWith(str)){
                    if(found != null){
                        throw new IllegalArgumentException("month is ambiguous");
                    }
                    found = month;
                }
            } 
            if(found == null){
               throw new IllegalArgumentException("month non found");   
            }
            return found;
        }
    }    

    @Override
    public Comparator<String> sortByDays() {
        return new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                final Month m1 = Month.fromString(o1);
                final Month m2 = Month.fromString(o2);
                return Integer.compare(m1.days, m2.days);
            }
            
        };
        
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                final Month m1 = Month.fromString(o1);
                final Month m2 = Month.fromString(o2);
                return Integer.compare(m1.ordinal(), m2.ordinal());
            }
            
        };
    }
}
