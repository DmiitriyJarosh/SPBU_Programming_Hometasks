{-# LANGUAGE LambdaCase #-}
module Sequent where
import Control.Monad

data Term = Not Term
          | Or Term Term
          | And Term Term
          | Impl Term Term
          | Var String
          | Empty
          | Shtp [Term] [Term]
    deriving Eq

instance Show Term where
  show (Not term) = "!" ++ show term ++ ""
  show (Or terml termr) = "(" ++ show terml ++ " or " ++ show termr ++ ")"
  show (And terml termr) = "(" ++ show terml ++ " and " ++ show termr ++ ")"
  show (Impl terml termr) = "(" ++ show terml ++ " -> " ++ show termr ++ ")"
  show (Var name) = name
  show Empty = "Empty"
  show (Shtp terml termr) = show terml ++ " |- " ++ show termr


eval :: (Term, String) -> [(Term, String)]
eval term = case term of
  (Shtp (Empty:terml) (Empty:termr), str) -> return  term
  (Shtp (xl:terml) termr@(Empty:_), str) -> case xl of
    Not arg -> eval (Shtp terml (arg:termr), linesDivider ++ show (deleteEmptyinLog (Shtp (xl:terml) termr)) ++ str)
    Or argl argr -> eval (Shtp (argl:terml) termr, "  |  " ++ show (deleteEmptyinLog (Shtp (argr:terml) termr)) ++ linesDivider ++ show (deleteEmptyinLog (Shtp (xl:terml) termr)) ++ str) `mplus` eval (Shtp (argr:terml) termr, "  |  " ++ show (deleteEmptyinLog (Shtp (argl:terml) termr)) ++ linesDivider ++ show (deleteEmptyinLog (Shtp (xl:terml) termr)) ++ str)
    And argl argr -> eval (Shtp (argl:(argr:terml)) termr, linesDivider ++ show (deleteEmptyinLog (Shtp (xl:terml) termr)) ++ str)
    Impl argl argr -> eval (Shtp (argr:terml) termr, "  |  " ++ show (deleteEmptyinLog (Shtp terml (argl:termr))) ++ linesDivider ++ show (deleteEmptyinLog (Shtp (xl:terml) termr)) ++ str) `mplus` eval (Shtp terml (argl:termr), "  |  " ++ show (deleteEmptyinLog (Shtp (argr:terml) termr)) ++ linesDivider ++ show (deleteEmptyinLog (Shtp (xl:terml) termr)) ++ str)
    Var name -> eval (Shtp (terml ++ [xl]) termr, linesDivider ++ show (deleteEmptyinLog (Shtp (xl:terml) termr)) ++ str)
    Empty -> return (Shtp (xl:terml) termr, str)
  (Shtp terml (xr:termr), str) -> case xr of
    Not arg -> eval (Shtp (arg:terml) termr, linesDivider ++ show (deleteEmptyinLog (Shtp terml (xr:termr))) ++ str)
    Or argl argr -> eval (Shtp terml (argl:(argr:termr)), linesDivider ++ show (deleteEmptyinLog (Shtp terml (xr:termr))) ++ str)
    And argl argr -> eval (Shtp terml (argl:termr), "  |  " ++ show (deleteEmptyinLog (Shtp terml (argr:termr))) ++ linesDivider ++ show (deleteEmptyinLog (Shtp terml (xr:termr))) ++ str) `mplus` eval (Shtp terml (argr:termr), "  |  " ++ show (deleteEmptyinLog (Shtp (argl:terml) termr)) ++ linesDivider ++ show (deleteEmptyinLog (Shtp terml (xr:termr))) ++ str)
    Impl argl argr -> eval (Shtp (argl:terml) (argr:termr), linesDivider ++ show (deleteEmptyinLog (Shtp terml (xr:termr))) ++ str)
    Var name -> eval (Shtp terml (termr ++ [xr]), linesDivider ++ show (deleteEmptyinLog (Shtp terml (xr:termr))) ++ str)
    Empty -> return (Shtp terml (xr:termr), str)
  otherwise -> error "wrong input"
  where linesDivider = "\n---------------------------\n"

--В конец в левую и правую часть штопора добавляет Empty. Используется один раз перед началом вычисления.
addEmptyToEnd :: Term -> Term
addEmptyToEnd = \ case
  Shtp terml termr -> Shtp (terml ++ [Empty]) (termr ++ [Empty])
  _ -> error "addEmptyToEnd wrong use"


--Удаляет Empty в листе после вычисления eval
deleteEmpty :: [(Term, String)] -> [(Term, String)]
deleteEmpty [] = []
deleteEmpty ((term, str):xs) = (deleteEmptyinLog term, str):deleteEmpty xs
--Убирает Empty в момент записи в лог и после вычисления eval
deleteEmptyinLog :: Term -> Term
deleteEmptyinLog = \case
  Shtp terml termr -> Shtp (filter (Empty /=) terml) (filter (Empty /=) termr)
  _ -> error "deleteEmptyinLog wrong use"
{-
Удаляет лишнюю начальную черту-разделитель в каждом логе
Число 29 захардкоженая длина разделительной черты "\n---------------------------\n"
-}
deleteSeparator :: [(Term, String)] -> [(Term, String)]
deleteSeparator [] = []
deleteSeparator ((term, str):xs) = (term, drop 29 str):deleteSeparator xs


{-
  Проверяет, является ли формула тавтологией или сущетвует ли контр-пример
  Если формула тавтология и контрпримера нет, то выводим сообщение об этом и все ветки вычислений
  Если формула не тавтология и контрпример есть, то выводим сообщение об этом, интерпретацию-контрпример и ветку вычислений, в которой этот контрпример был достигнут.
-}
checker :: [(Term, String)] -> Integer -> String -> String
checker [] num strtree = "This formula is tautology!" ++ "\n" ++ strtree
checker ((Shtp terml termr, str):xs) num strtree = if findSimilars terml termr then checker xs (num + 1) (strtree ++ "\n" ++ "Computation branch #" ++ show num ++ "\n" ++ str ++ "\n") else "Counterexample exists! \nCounterexample: " ++ findCounterExample (Shtp terml termr) ++ "\n" ++ "Branch with counterexample\n" ++ str

--Проверяет, найдется ли хотя бы одна переменная, которая стоит и справа, и слева.
findSimilars :: [Term] -> [Term] -> Bool
findSimilars [] y = False
findSimilars (x:xs) y = if findInList x y then True else findSimilars xs y
--Проверяет, содержит ли масссив элемент
findInList :: Term -> [Term] -> Bool
findInList x [] = False
findInList x (y:ys) = if x == y then True else findInList x ys


--Выводим контрпример
findCounterExample :: Term -> String
findCounterExample (Shtp terml termr) = "True: " ++ concatMap show (deleteCopies terml) ++ " | False: " ++ concatMap show (deleteCopies termr)
--Удаляет дубликаты термов
deleteCopies :: [Term] -> [Term]
deleteCopies [] = []
deleteCopies (x:xs) = x : deleteCopies (filter (x /=) xs)


--Главная функция - принимает терм-секвенцию и возвращает ответ в файл "result.txt"
mainEval :: Term -> IO ()
mainEval term = writeFile "result.txt" (checker (deleteSeparator (deleteEmpty (eval (addEmptyToEnd term, "")))) 1 "")

test = Shtp [] [Impl (Not (And (Var "p") (Var "p"))) (Var "p")]
test1 = Shtp [] [Not(Not(Var "p"))]
test2 = Shtp [] [And (Var "q") (Var "x")]
test3 = Shtp [] [Not (And (Or (Not (Var "p")) (Var "q")) (Or (Not (Var "q")) (Var "p")))]
test4 = Shtp [] [Or (Not (And (Or (Not (Var "p")) (Var "q")) (Or (Not (Var "q")) (Var "p")))) (Not (Or (And (Var "p") (Not (Var "q"))) (And (Var "q") (Not (Var "p")))))]
test5 = Shtp [] [Or (Var "x") (Not(Var "x"))]
