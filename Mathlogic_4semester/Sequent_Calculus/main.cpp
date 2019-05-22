#include <iostream>
#include "Formula.h"
#include "TwoArgsFormula.h"
#include "OneArgFormula.h"
#include "Variable.h"
#include "Solver.h"
#include "Parser.h"
#include <fstream>


int main() {
//    std::string a = "a";
//    Formula *input = new TwoArgsFormula(new OneArgFormula(new Variable(a), Negation), new Variable(a), Conjunction);
    std::string input;
    std::getline(std::cin, input);
    Parser* parser = new Parser();
    Formula* inputFormula = parser->parse(input);
    std::cout << "Parsing result: " << inputFormula->toString() << std::endl;
    auto *leftArg = new std::vector<Formula*>();
    auto *rightArg = new std::vector<Formula*>();
    rightArg->push_back(inputFormula);
    auto sequence = new Sequence(leftArg, rightArg);
    auto solver = new Solver();
    solver->solve(sequence, parser->getVariables());
    return 0;
}
//((!a ^ a) -> (!a ^ a))
//((!a v a) -> (!a ^ a))