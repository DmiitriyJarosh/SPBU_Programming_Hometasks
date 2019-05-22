package mathlib;

public interface function {
    double getValue(double X, boolean positivness, double step);
    double getDeadPoint();
    double getDeadSphereRadius();
    double getEnrichStepCoeff();
}
