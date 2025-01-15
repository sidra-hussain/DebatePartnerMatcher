# Debate Partner Matcher  

## Overview  
This project focuses on optimizing debate partner pairings in APDA-style parliamentary debates. By leveraging probabilistic role-based performance data, the algorithms aim to maximize the overall probability of winning for teams. The problem is modeled as a combinatorial optimization task and includes multiple approaches to find the best matchings.  

## Features  
- **Brute Force Algorithm**: Explores all possible pairings to find the optimal solution (used for validation).  
- **Greedy Approach**: Matches the strongest debater with their best complement, prioritizing immediate high probabilities.  
- **Strongest Pair Algorithm**: Matches the highest-scoring pair first and iterates until all debaters are paired.  

## Key Highlights  
- Handles probabilistic data representing debaters' performance in specific roles.  
- Supports analysis of valid role pairings (e.g., PM-MG, LO-MO) to ensure team compatibility.  
- Includes heuristic methods to address the exponential nature of the problem.  

## Requirements  
- Python 3.x  
- Libraries: `numpy`, `itertools` (or others as needed for your implementation).  

## How It Works  
1. **Input**: Randomized probability data for debaters across valid role pairings.  
2. **Algorithms**: Implements three approaches to solve the pairing problem:  
   - Brute Force (Exact Solution).  
   - Greedy Approach.  
   - Strongest Pair Matching.  
3. **Output**: Pairings with associated probabilities of success for the team.

## Limitations 
- The brute force method is computationally expensive and limited to smaller datasets.
- Heuristic methods may not always yield the optimal solution but provide practical approximations.

## Usage  
1. Clone this repository:  
   ```bash  
   git clone https://github.com/yourusername/Debate-Partner-Matcher.git  
   cd Debate-Partner-Matcher ```
2. Run the project
   ```bash
   python match_debaters.py ```
