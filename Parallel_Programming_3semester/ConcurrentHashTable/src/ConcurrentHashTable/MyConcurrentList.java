package ConcurrentHashTable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyConcurrentList<T> {

    private Node<T> head;
    private Lock modifyLock;

    public MyConcurrentList() {
        head = null;
        modifyLock = new ReentrantLock();
    }

    public boolean Contains (T elem) {
        return contains(elem);
    }

    private boolean contains(T elem) {
        Node<T> tmp;
        if (head == null) {
            return false;
        }
        tmp = head;
        if (tmp.getValue().equals(elem)) {
            return true;
        } else {
            while (tmp.getNext() != null) {
                tmp = tmp.getNext();
                if (tmp.getValue().equals(elem)) {
                    return true;
                }
            }
            return false;
        }
    }


    private void remove(T elem) {
        Node <T> tmp, tmpPrev;
        tmp = head;
        if (tmp.getValue().equals(elem)) {
            head = head.getNext();
        } else {
            while (tmp.getNext() != null) {
                tmpPrev = tmp;
                tmp = tmp.getNext();
                if (tmp.getValue().equals(elem)) {
                    tmpPrev.setNext(tmp.getNext());
                }
            }
        }
    }

    private void append(T elem) {
        Node<T> newNode = new Node<>(elem);
        newNode.setNext(head);
        head = newNode;
    }

    public void add(T elem) {
        cloneAndModify(elem, false);
    }

    public void delete(T elem) {
        cloneAndModify(elem, true);
    }

    protected MyConcurrentList<T> clone() {
        MyConcurrentList<T> newList = new MyConcurrentList<>();
        if (head == null) {
            return newList;
        } else {
            newList.head = new Node<>(head.getValue());
            Node<T> tmpThis = head;
            Node<T> tmpNew = newList.head;
            while (tmpThis.getNext() != null) {
                tmpThis = tmpThis.getNext();
                tmpNew.setNext(new Node<>(tmpThis.getValue()));
                tmpNew = tmpNew.getNext();
            }
            return newList;
        }
    }

    private void cloneAndModify(T elem, boolean flag) {
        try {
            modifyLock.lock();
            MyConcurrentList<T> clonedList = this.clone();
            if (clonedList.contains(elem) && flag) {
                clonedList.remove(elem);
            } else if (!clonedList.Contains(elem) && !flag) {
                clonedList.append(elem);
            }
            head = clonedList.head;
        } finally {
            modifyLock.unlock();
        }
    }
}
