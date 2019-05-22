package ConcurrentHashTable;

public class HashTable implements IExamSystem {

    private volatile MyConcurrentList<Exam>[] table;

    public HashTable() {
        table = new MyConcurrentList[1000000];
        for (int i = 0; i < table.length; i++) {
            table[i] = new MyConcurrentList<>();
        }
    }

    public int hash(long studentId) {
        return Long.toString(studentId).hashCode() % 1000000;
    }


    @Override
    public boolean Contains(long studentId, long courseId) {
        int hash = hash(studentId);
        return table[hash].Contains(new Exam(studentId, courseId));
    }

    @Override
    public void Add(long studentId, long courseId) {
        int hash = hash(studentId);
        table[hash].add(new Exam(studentId, courseId));
    }

    @Override
    public void Remove(long studentId, long courseId) {
        int hash = hash(studentId);
        table[hash].delete(new Exam(studentId, courseId));
    }
}
