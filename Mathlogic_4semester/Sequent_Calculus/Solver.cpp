//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#include <iostream>
#include "Solver.h"
#include "Variable.h"
#include <sstream>


template <typename T>
std::string toString(T val)
{
    std::ostringstream oss;
    oss<< val;
    return oss.str();
}

Solver::Solver() {
    this->workQueue = new std::queue<std::pair<Sequence*, int>* >();
    this->filePrinter = new FilePrinter(std::string("output.txt"));
    filePrinter->printToFile("digraph G {\nrankdir = BT;\n");
}

void Solver::solve(Sequence *input, std::set<Variable*>* variables) {
    int nodeCounter = 1;
    filePrinter->printToFile("node0 [label = \"" + input->toString() + "\"]\n");
    workQueue->push(new std::pair<Sequence*, int>(input, 0));
    workQueue->push(nullptr); //divides levels of counting
    Sequence *current;
    std::pair<Sequence*, int> *currentPair;
    std::string output = "";
    std::ostringstream stringStream;
    std::string antiExample = "", tmp;
    std::string currentToString;
    std::pair<Sequence*, Sequence*> *parentSequences;
    bool antiExampleFoundFlag = false;
    while (!workQueue->empty()) {
        currentPair = workQueue->front();
        workQueue->pop();
        if (currentPair == nullptr) {
            filePrinter->printToFile(output);
            output = "";
            if (!workQueue->empty()) {
                workQueue->push(nullptr);
            }
            continue;
        }

        current = currentPair->first;
        currentToString = current->toString();
        parentSequences = current->getParentSequnces();

        if (parentSequences == nullptr) {
            tmp = findAntiExample(current, variables);
            if (tmp != "") {
                if (!antiExampleFoundFlag) {
                    antiExampleFoundFlag = true;
                    antiExample = tmp;
                    output += "node" + toString(currentPair->second) + " [color = \"red\"]\n";
                } else {
                    output += "node" + toString(currentPair->second) + " [color = \"green\"]\n";
                }
            } else {
                output += "node" + toString(currentPair->second) + " [color = \"blue\"]\n";
            }
            continue;
        }

        int leftNodeNum = nodeCounter;
        nodeCounter++;
        output += "node" + toString(leftNodeNum) + " [label = \"" + parentSequences->first->toString() + "\"]\n";
        output += "node" + toString(currentPair->second) + " -> " + "node" + toString(leftNodeNum) + ";\n";


        workQueue->push(new std::pair<Sequence*, int>(parentSequences->first, leftNodeNum));
        if (parentSequences->second != nullptr) {
            int rightNodeNum = nodeCounter;
            nodeCounter++;
            output += "node" + toString(rightNodeNum) + " [label = \"" + parentSequences->second->toString() + "\"]\n";
            workQueue->push(new std::pair<Sequence*, int>(parentSequences->second, rightNodeNum));
            output += "node" + toString(currentPair->second) + " -> " + "node" + toString(rightNodeNum) + ";\n";
        }
    }

    filePrinter->printToFile("}");

    if (antiExampleFoundFlag) {
        std::cout << "Antiexample exists!!! Antiexample: " << antiExample << std::endl;
    } else {
        std::cout << "This formula is tautology!!!" << std::endl;
    }
}

std::string Solver::findAntiExample(Sequence *sequence, std::set<Variable*> *variables) {
    std::vector<Formula*> * leftArg = sequence->getLeftArg();
    std::vector<Formula*> * rightArg = sequence->getRightArg();
    sequence->deleteDuplicates();
    for (int i = 0; i < leftArg->size(); i++) {
        for (int j = 0; j < rightArg->size(); j++) {
            if ((*(Variable*)leftArg->at(i)) == (*(Variable*)rightArg->at(j))) {
                return "";
            }
        }
    }
    std::string antiExample = "True: ";
    for (int iter = 0; iter < leftArg->size(); iter++) {
        antiExample += ((Variable*)leftArg->at(iter))->toString() + " ";
        variables->erase((Variable*)leftArg->at(iter));
    }
    antiExample += "| False: ";
    for (int iter = 0; iter < rightArg->size(); iter++) {
        antiExample += ((Variable*)rightArg->at(iter))->toString() + " ";
        variables->erase((Variable*)rightArg->at(iter));
    }
    for (auto item : *variables) {
        antiExample += item->toString() + " ";
    }
    return antiExample;
}