## Referential transparency proof of concept

This is a very simple example that should who why it matter to have a code that is referential transparent.

Definition:  
_An expression is called **referentially transparent** if it can be replaced with its corresponding value without changing the program's behavior. This requires that the expression be pure, that is to say the expression value must be the same for the same inputs and its evaluation must have no side effects. An expression that is not referentially transparent is called referentially opaque._

In the test ```ReferentialTransparencyExampleTest```  we see that we cannot remove the duplication
on program p1 because it is not referentially transparent

There are two solution that are instead referential transparent in which the refactor can be done
- a custom solution
- a solution that uses IO monad from arrow-kt 
