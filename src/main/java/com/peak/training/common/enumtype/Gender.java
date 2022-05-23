package com.peak.training.common.enumtype;


public class Gender {
    public enum TYPE{
        Male('M'),
        Female('F'),
        Other('O');

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
