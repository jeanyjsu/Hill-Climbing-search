/***
 * MapColoring.java
 * Ya Ching Su
 * T00645580
 * COMP 3711 Assignment 3
 * This is a java software that takes the k variable from the user and uses the hill-climbing search to color the map.
 * The program will print the provinces and it's color in the same order as the the ones given in part 1 question 1.
 */

import java.util.*;
public class MapColoring {
     public static void main(String[] args) {
          //initializing the map and it's adjacency matrix using the graph provided in part 1.
          //the program first ask the user for the k value, I purposefully set a while loop to ensure that
          //k value is at least 4. Reason being apparently based on the map coloring theorem, with four colors
          //you can color any map that would satisfy the condition that the assignment is requesting.
          int k;
          int[][] adjacencyMatrix = {
                  {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                  {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                  {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                  {0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                  {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                  {0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0},
                  {0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0},
                  {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                  {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                  {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                  {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                  {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                  {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}
          };
          String[] province = {"BC", "AB", "SK", "MB", "ON", "QC", "NB", "NS", "PEI", "NL", "NU", "NT", "YT"};
          int[] initialMap = new int[13];
          Scanner scan = new Scanner(System.in);
          System.out.println("Please enter how many colors you want to use: ");
          k = scan.nextInt();
          while (k < 4) {
               System.out.println("please enter a number larger than 3: ");
               k = scan.nextInt();
          }

          //The program then asks the user for the colors they want to use, and store them into an array.
          String[] colors = new String[k];
          scan.nextLine();
          for (int i = 0; i < k; i++) {
               System.out.println("Enter the color: ");
               colors[i] = scan.nextLine();
          }
          System.out.println("Colors that are being used: " + Arrays.toString(colors));

          //randomly assign the colors into the provinces
          Random rand = new Random();
          for (int i = 0; i < province.length; i++) {
               initialMap[i] = rand.nextInt(colors.length);
          }
          printColorMap(province, initialMap, colors);

          //setting a limit with the number of iterations the program can do until it reaches the goal state.
          //i tried using a while-loop with the checkAdjacentColor method, but failed miserably and ended up
          //with an infinite loop instead.
          //Anyways this step compares the cost of the current map with the possible new map using the calculateCost
          //method. Then replaces the current map with a new one, if the new one costs less.
          //repeat until either the goal is reach, or the maximum iteration limit is reached.
          int numIteration = 1000;
          for(int i = 0; i < numIteration; i++)
         {
              int[] nextMap = changeColor(initialMap, colors, adjacencyMatrix);
              if (calculateCosts(nextMap, adjacencyMatrix) < calculateCosts(initialMap, adjacencyMatrix))
              {
                         initialMap = nextMap;
              }
         }
               System.out.println("--------------------------Final map colors---------------------------- ");
               printColorMap(province, initialMap, colors);
     }

     //generates a new map, nextMap, and then randomly generate a new color. Using the checkAdjacentColor to see
     // if the adjacent provinces of the current province in the current map(not nextMap) are the same color.
     //If they are, then the color is changed to a new random color
     private static int[] changeColor(int[] currentMap, String[] colors,  int[][] adjacencyMatrix)
     {
          int[] nextMap = Arrays.copyOf(currentMap, currentMap.length);
          Random rand = new Random();
          int index = rand.nextInt(nextMap.length);
          int color = rand.nextInt(colors.length);

          if(checkAdjacentColor(index, color, currentMap, adjacencyMatrix))
          {
               color = rand.nextInt(colors.length);
          }
          nextMap[index] = color;
          return nextMap;
     }

     //checking the provinces that are adjacent(==1) and their color based on the map color that was randomly
     //assigned from main
     private static boolean checkAdjacentColor(int index, int colors, int[] initialMap, int[][] adjacencyMatrix)
     {
          for(int i = 0; i < adjacencyMatrix[index].length; i++)
          {
               if(adjacencyMatrix[index][i] == 1 && initialMap[i] == colors)
               {
                    return true;
               }
          }
          return false;
     }

     //I am confused on how the cost function in part 1 is going to be applied to this java program as the user might
     //not choose the color that is listed in part 1. So i can't assign a cost to them, plus, i can't guarantee
     //that the user would choose only 4 colors. So now they have an uniform cost of 1.
     private static int calculateCosts(int[] map, int[][] adjacencyMatrix)
     {
          int HeuristicCost = 0;
          for(int i = 0; i < adjacencyMatrix.length; i++)
          {
               for(int j = i + 1; j < adjacencyMatrix[i].length; j++)
               {
                    if(adjacencyMatrix[i][j] == 1 && map[i] == map[j])
                    {
                         HeuristicCost++;
                    }
               }
          }
          return HeuristicCost;
     }

     //display the province and their color
     static void printColorMap(String[] provinces, int[] initialMap, String[] colors)
     {
          for(int i = 0; i < provinces.length; i++)
          {
               String province = provinces[i];
               String color = colors[initialMap[i]];
               System.out.println(province + ": " + color);
          }
     }
}



