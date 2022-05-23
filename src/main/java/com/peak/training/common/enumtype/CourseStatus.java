package com.peak.training.common.enumtype;


public class CourseStatus {
    public enum TYPE{
        ACTIVE('A'),
        DELETED('D'),
        PENDING('P');

        private final char value;

        private TYPE(final char newValue) {
            value = newValue;
        }

        public char getValue() { return value; }

        public static TYPE getTypeByChar(char s) {
            for(TYPE v : values()){
                if( v.getValue()== s){
                    return v;
                }
            }
            return null;
        }


    }
}
