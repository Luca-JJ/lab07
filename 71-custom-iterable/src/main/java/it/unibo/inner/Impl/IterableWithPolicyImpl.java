package it.unibo.inner.Impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final T[] elements;
    private Predicate<T> predicate;

    public IterableWithPolicyImpl(T[] elements, Predicate<T> predicate) {
        this.elements = elements;
        this.predicate = predicate;
    }   
    
    public IterableWithPolicyImpl(T[] elements) {
        this(elements, new Predicate<T>() {
            public boolean test(T elem){
                return true;
            }
        });
        
    }

    @Override
    public Iterator<T> iterator() {
        return this.new IteratorWithPolicy();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.predicate = filter;
    }

    private class IteratorWithPolicy implements Iterator<T>{

        private int current;

        public IteratorWithPolicy(){
            this.current = 0;
        }

        @Override
        public boolean hasNext() {
            while(current < IterableWithPolicyImpl.this.elements.length){
                T elem = IterableWithPolicyImpl.this.elements[current];
                if(IterableWithPolicyImpl.this.predicate.test(elem)){
                    return true;
                }
                this.current++;
            }
            return false;
        }

        @Override
        public T next() {
            if(hasNext()){

                return IterableWithPolicyImpl.this.elements[current++];
            }
            throw new NoSuchElementException();
        }
        
    }

}
