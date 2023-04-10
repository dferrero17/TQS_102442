# Quality Gate

The project passed the quality gate as there are no vulnerabilities and there is only one bug in our project.


# Bugs

## Bug #1

**Problem Description** 
* Save and re-use this "Random".

**How to Solve**
* Create one single instance of the "Random", store it and reuse it when needed instead of creating a new Random everytime we need to generate a Random number.

# Vulnerabilities

No vulnerabilities found. 1 Security hostpot found.

## Security Hotspot

**Problem Description** 
* Make sure that using this pseudorandom number generator is safe here.

**How to solve**
* No need to solve.


# Code Smell

## Code Smell #1

**Problem Description** 
* Refactor the code in order to not assign to this loop counter from within the loop body.

**How to solve**
* In Dip.java file, instead of incremnenting the for loop counter (i) inside the for loop, increment it while defining the for loop itself.

## Code Smell #2

**Problem Description**
* Replace this use of System.out or System.err by a logger.

**How to solve**
* In DemoMain.java instead of using System.out.println to print things to console we should use a logger.





 


