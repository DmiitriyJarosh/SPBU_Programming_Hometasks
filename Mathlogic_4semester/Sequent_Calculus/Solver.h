//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#ifndef SEQUENCE_SOLVER_H
#define SEQUENCE_SOLVER_H


#include "Sequence.h"
#include "FilePrinter.h"
#include "Variable.h"
#include <queue>
#include <fstream>
#include <set>

class Solver {

private:
    std::queue<std::pair<Sequence*, int>* > *workQueue;
    FilePrinter *filePrinter;

public:
    Solver();
    void solve(Sequence *input, std::set<Variable*> *variables);
    std::string findAntiExample(Sequence *sequence, std::set<Variable*> *variables);
};


#endif //SEQUENCE_SOLVER_H
