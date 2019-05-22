//
// Created by Dmitrii Iarosh on 07.03.2019.
//

#include "Sequence.h"
#include "OneArgFormula.h"
#include "TwoArgsFormula.h"
#include "Variable.h"

Sequence::Sequence(int leftArgSize, int rightArgSize) {
    this->leftArg = new std::vector<Formula*>(leftArgSize);
    this->rightArg = new std::vector<Formula*>(rightArgSize);
}

Sequence::Sequence(std::vector<Formula *> *leftArg, std::vector<Formula *> *rightArg) {
    this->rightArg = rightArg;
    this->leftArg = leftArg;
}

std::vector<Formula*>* Sequence::getLeftArg() {
    return leftArg;
}

std::vector<Formula*>* Sequence::getRightArg() {
    return rightArg;
}

std::string Sequence::toString() {
    std::string leftPart = "", rightPart = "";
    for (int i = 0; i < leftArg->size(); i++) {
        leftPart += leftArg->at(i)->toString();
        if (leftArg->size() - i > 1) {
            leftPart += ", ";
        }
    }
    for (int i = 0; i < rightArg->size(); i++) {
        rightPart += rightArg->at(i)->toString();
        if (rightArg->size() - i > 1) {
            rightPart += ", ";
        }
    }
#ifdef UNICODE
    return leftPart + " \u22A2 " + rightPart;
#else
    return leftPart + " |- " + rightPart;
#endif

}

void Sequence::deleteDuplicates() {
    for (int i = 0; i < leftArg->size(); i++) {
        for (int j = i + 1; j < leftArg->size(); j++) {
            if ((*(Variable*)(leftArg->at(i))) == (*(Variable*)(leftArg->at(j)))) {
                leftArg->erase(leftArg->begin() + j);
            }
        }
    }
    for (int i = 0; i < rightArg->size(); i++) {
        for (int j = i + 1; j < rightArg->size(); j++) {
            if ((*(Variable*)rightArg->at(i)) == (*(Variable*)rightArg->at(j))) {
                rightArg->erase(rightArg->begin() + j);
            }
        }
    }
}

std::pair<Sequence*, Sequence*> *Sequence::getParentSequnces() {
    bool stepMadeFlag = false;
    Sequence *leftSequence = this;
    Sequence *rightSequence = nullptr;
    for (int i = 0; i < leftArg->size(); i++) {
        if (stepMadeFlag) {
            break;
        }
        switch(leftArg->at(i)->getOperandType()) {
            case Negation:
                leftSequence->rightArg->push_back(((OneArgFormula*)(leftArg->at(i)))->getArg());
                leftSequence->leftArg->erase(leftSequence->leftArg->begin() + i);
                stepMadeFlag = true;
                break;
            case Conjunction:
                leftSequence->leftArg->push_back(((TwoArgsFormula*)(leftArg->at(i)))->getLeftArg());
                leftSequence->leftArg->push_back(((TwoArgsFormula*)(leftArg->at(i)))->getRightArg());
                leftSequence->leftArg->erase(leftSequence->leftArg->begin() + i);
                stepMadeFlag = true;
                break;
            case Disjunction:
                rightSequence = new Sequence(leftArg->size(), rightArg->size());
                std::copy(leftArg->begin(), leftArg->end(), rightSequence->leftArg->begin());
                std::copy(rightArg->begin(), rightArg->end(), rightSequence->rightArg->begin());
                leftSequence->leftArg->push_back(((TwoArgsFormula*)(leftArg->at(i)))->getLeftArg());
                rightSequence->leftArg->push_back(((TwoArgsFormula*)(leftArg->at(i)))->getRightArg());
                leftSequence->leftArg->erase(leftSequence->leftArg->begin() + i);
                rightSequence->leftArg->erase(rightSequence->leftArg->begin() + i);
                stepMadeFlag = true;
                break;
            case Implication:
                rightSequence = new Sequence(leftArg->size(), rightArg->size());
                std::copy(leftArg->begin(), leftArg->end(), rightSequence->leftArg->begin());
                std::copy(rightArg->begin(), rightArg->end(), rightSequence->rightArg->begin());
                leftSequence->rightArg->push_back(((TwoArgsFormula*)(leftArg->at(i)))->getLeftArg());
                rightSequence->leftArg->push_back(((TwoArgsFormula*)(leftArg->at(i)))->getRightArg());
                leftSequence->leftArg->erase(leftSequence->leftArg->begin() + i);
                rightSequence->leftArg->erase(rightSequence->leftArg->begin() + i);
                stepMadeFlag = true;
                break;
        }
    }

    if (stepMadeFlag) {
        return new std::pair<Sequence*, Sequence*>(leftSequence, rightSequence);
    }

    for (int i = 0; i < rightArg->size(); i++) {
        if (stepMadeFlag) {
            break;
        }
        switch(rightArg->at(i)->getOperandType()) {
            case Negation:
                leftSequence->leftArg->push_back(((OneArgFormula*)(rightArg->at(i)))->getArg());
                leftSequence->rightArg->erase(leftSequence->rightArg->begin() + i);
                stepMadeFlag = true;
                break;
            case Conjunction:
                rightSequence = new Sequence(leftArg->size(), rightArg->size());
                std::copy(leftArg->begin(), leftArg->end(), rightSequence->leftArg->begin());
                std::copy(rightArg->begin(), rightArg->end(), rightSequence->rightArg->begin());
                leftSequence->rightArg->push_back(((TwoArgsFormula*)(rightArg->at(i)))->getLeftArg());
                rightSequence->rightArg->push_back(((TwoArgsFormula*)(rightArg->at(i)))->getRightArg());
                leftSequence->rightArg->erase(leftSequence->rightArg->begin() + i);
                rightSequence->rightArg->erase(rightSequence->rightArg->begin() + i);
                stepMadeFlag = true;
                break;
            case Disjunction:
                leftSequence->rightArg->push_back(((TwoArgsFormula*)(rightArg->at(i)))->getLeftArg());
                leftSequence->rightArg->push_back(((TwoArgsFormula*)(rightArg->at(i)))->getRightArg());
                leftSequence->rightArg->erase(leftSequence->rightArg->begin() + i);
                stepMadeFlag = true;
                break;
            case Implication:
                leftSequence->leftArg->push_back(((TwoArgsFormula*)(rightArg->at(i)))->getLeftArg());
                leftSequence->rightArg->push_back(((TwoArgsFormula*)(rightArg->at(i)))->getRightArg());
                leftSequence->rightArg->erase(leftSequence->rightArg->begin() + i);
                stepMadeFlag = true;
                break;
        }
    }

    if (stepMadeFlag) {
        return new std::pair<Sequence*, Sequence*>(leftSequence, rightSequence);
    }

    return nullptr;
}