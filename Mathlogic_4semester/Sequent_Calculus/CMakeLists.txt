cmake_minimum_required(VERSION 3.10)
project(Sequence)

set(CMAKE_CXX_STANDARD 11)
add_definitions(-D UNICODE)
add_executable(Sequent_Calculus main.cpp Formula.h TwoArgsFormula.cpp TwoArgsFormula.h OneArgFormula.cpp OneArgFormula.h Variable.cpp Variable.h Solver.cpp Solver.h Sequence.cpp Sequence.h Parser.cpp Parser.h FilePrinter.h)