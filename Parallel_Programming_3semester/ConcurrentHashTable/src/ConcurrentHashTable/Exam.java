package ConcurrentHashTable;

public class Exam {

    private long studentId;
    private long courseId;

    public Exam(long studentId, long courseId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Exam) {
            Exam exam = (Exam) obj;
            return (exam.courseId == courseId && exam.studentId == studentId);
        }
        return super.equals(obj);
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
