import java.io.*;
import java.util.*;

public class Main {
    public static int score;      // game score
    public static int white_col;  // it is a column where "*" (white ball) is located.
    public static int white_row;  // it is a line of board where "*" is located.

    // it is a method for printing the desired to output file.
    public static void write_output(String write_data){
        try {
            File file = new File("output.txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write(write_data);
            fr.close();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }

    // This method is used when ("*") white ball moves to next place
    public static void after_collision(String next_cell, ArrayList<ArrayList<String>> board){
        if (Objects.equals(next_cell, "R")) {
            score += 10;                               // if the next place is red("R"), it gets 10 point.
            board.get(white_row).set(white_col, "X");  // "*" place before the move replaces with "X"
        }
        else if (Objects.equals(next_cell, "B")) {
            score -= 5;                                // if the next place is black("B"), it gets 10 point.
            board.get(white_row).set(white_col, "X");
        }
        else if (Objects.equals(next_cell, "Y")) {
            score += 5;                                // if the next place is yellow("Y"), it gets 10 point.
            board.get(white_row).set(white_col, "X");
        }
        else if (Objects.equals(next_cell, "X")) {
            board.get(white_row).set(white_col, "X");
        }
        else {  // if it is another colour, "*" place before the move replaces with colour.
            board.get(white_row).set(white_col, next_cell);
        }
    }

    public static void main(String[] args) throws IOException {
        // board is 2d array list which is given in board.txt
        ArrayList<ArrayList<String>> board = new ArrayList<>();
        BufferedReader br_file = new BufferedReader(new FileReader("board.txt"));
        String line;
        while ((line = br_file.readLine()) != null) {
            String[] values = line.split(" ");  // values is used for split
            ArrayList<String> row = new ArrayList<>(Arrays.asList(values)); // row is one line in board.txt
            board.add(row);
        }
        br_file.close();

        // moves is array list which is given in move.txt
        BufferedReader move_file = new BufferedReader(new FileReader("move.txt"));
        String[] move_arr = move_file.readLine().split(" ");
        ArrayList<String> moves = new ArrayList<String>(Arrays.asList(move_arr));
        move_file.close();

        // the maximum board is 20x20 grid and the maximum move is 30
        if (board.size() < 21 && board.get(0).size() < 21 && moves.size() < 31) {
            write_output("Game board: \n");
            board.forEach(row -> {
                for (Object cell : row) {
                    write_output(cell + " ");
                }
                write_output("\n");
            });
            write_output("\n");
            score = 0;
            String move_output = "";  // Print the moves which are applied to output file

            for (String move : moves) {
                move_output += move + " ";
                // find the cell of white ball in board
                for (int i = 0; i < board.size(); i++) {
                    if (board.get(i).contains("*")) {
                        white_row = i;  // white ball is in this line
                        white_col = board.get(i).indexOf("*"); // white ball index in its line
                        break;
                    }
                }
                // if move is left("L")
                if (Objects.equals(move, "L")) {
                    try {
                        // move left in the same line for white ball
                        String next_place = board.get(white_row).get(white_col - 1);
                        if (Objects.equals(next_place, "W")) {
                            try {
                                String wall_collision = board.get(white_row).get(white_col + 1);
                                if (Objects.equals(wall_collision, "H")) {
                                    board.get(white_row).set(white_col, " ");
                                    break;
                                }
                                after_collision(wall_collision, board);
                                board.get(white_row).set(white_col + 1, "*");
                            }
                            // if the ball is in the last index of the line before collision with wall
                            catch (IndexOutOfBoundsException e) {
                                String wall_collision = board.get(white_row).get(0);
                                if (Objects.equals(wall_collision, "H")) {
                                    board.get(white_row).set(white_col, " ");
                                    break;
                                }
                                after_collision(wall_collision, board);
                                board.get(white_row).set(0, "*");
                            }
                        } else if (Objects.equals(next_place, "H")) { // if the next cell is H(hole)
                            board.get(white_row).set(white_col, " ");
                            break;
                        } else { // next cell is not W or H, call the game function
                            after_collision(next_place, board);
                            board.get(white_row).set(white_col - 1, "*");
                        }
                    }
                    // if "*" is in the first index of line before the move
                    catch (IndexOutOfBoundsException e) {
                        String next_place = board.get(white_row).get(board.get(white_row).size() - 1);
                        if (Objects.equals(next_place, "W")) {
                            String wall_collision = board.get(white_row).get(1);
                            if (Objects.equals(wall_collision, "H")) {
                                board.get(white_row).set(white_col, " ");
                                break;
                            }
                            after_collision(wall_collision, board);
                            board.get(white_row).set(1, "*");
                        } else if (Objects.equals(next_place, "H")) {
                            board.get(white_row).set(white_col, " ");
                            break;
                        } else {
                            after_collision(next_place, board);
                            board.get(white_row).set(board.get(white_row).size() - 1, "*");
                        }
                    }
                }
                // same operations for right("R"), up("U") and down("D")
                else if (Objects.equals(move, "R")) {
                    try {
                        String next_place = board.get(white_row).get(white_col + 1);
                        if (Objects.equals(next_place, "W")) {
                            try {
                                String wall_collision = board.get(white_row).get(white_col - 1);
                                if (Objects.equals(wall_collision, "H")) {
                                    board.get(white_row).set(white_col, " ");
                                    break;
                                }
                                after_collision(wall_collision, board);
                                board.get(white_row).set(white_col - 1, "*");
                            } catch (IndexOutOfBoundsException e) {
                                String wall_collision = board.get(white_row).get(board.get(white_row).size() - 1);
                                if (Objects.equals(wall_collision, "H")) {
                                    board.get(white_row).set(white_col, " ");
                                    break;
                                }
                                after_collision(wall_collision, board);
                                board.get(white_row).set(board.get(white_row).size() - 1, "*");
                            }
                        } else if (Objects.equals(next_place, "H")) {
                            board.get(white_row).set(white_col, " ");
                            break;
                        } else {
                            after_collision(next_place, board);
                            board.get(white_row).set(white_col + 1, "*");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        String next_place = board.get(white_row).get(0);
                        if (Objects.equals(next_place, "W")) {
                            String wall_collision = board.get(white_row).get(board.get(white_row).size() - 1);
                            if (Objects.equals(wall_collision, "H")) {
                                board.get(white_row).set(white_col, " ");
                                break;
                            }
                            after_collision(wall_collision, board);
                            board.get(white_row).set(board.get(white_row).size() - 1, "*");
                        } else if (Objects.equals(next_place, "H")) {
                            board.get(white_row).set(white_col, " ");
                            break;
                        } else {
                            after_collision(next_place, board);
                            board.get(white_row).set(0, "*");
                        }
                    }
                } else if (Objects.equals(move, "U")) {
                    try {
                        String next_place = board.get(white_row - 1).get(white_col);
                        if (Objects.equals(next_place, "W")) {
                            try {
                                String wall_collision = board.get(white_row + 1).get(white_col);
                                if (Objects.equals(wall_collision, "H")) {
                                    board.get(white_row).set(white_col, " ");
                                    break;
                                }
                                after_collision(wall_collision, board);
                                board.get(white_row + 1).set(white_col, "*");
                            } catch (IndexOutOfBoundsException e) {
                                String wall_collision = board.get(0).get(white_col);
                                if (Objects.equals(wall_collision, "H")) {
                                    board.get(white_row).set(white_col, " ");
                                    break;
                                }
                                after_collision(wall_collision, board);
                                board.get(0).set(white_col, "*");
                            }
                        } else if (Objects.equals(next_place, "H")) {
                            board.get(white_row).set(white_col, " ");
                            break;
                        } else {
                            after_collision(next_place, board);
                            board.get(white_row - 1).set(white_col, "*");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        String next_place = board.get(board.size() - 1).get(white_col);
                        if (Objects.equals(next_place, "W")) {
                            String wall_collision = board.get(1).get(white_col);
                            if (Objects.equals(wall_collision, "H")) {
                                board.get(white_row).set(white_col, " ");
                                break;
                            }
                            after_collision(wall_collision, board);
                            board.get(1).set(white_col, "*");
                        } else if (Objects.equals(next_place, "H")) {
                            board.get(white_row).set(white_col, " ");
                            break;
                        } else {
                            after_collision(next_place, board);
                            board.get(board.size() - 1).set(white_col, "*");
                        }
                    }
                } else if (Objects.equals(move, "D")) {
                    try {
                        String next_place = board.get(white_row + 1).get(white_col);
                        if (Objects.equals(next_place, "W")) {
                            try {
                                String wall_collision = board.get(white_row - 1).get(white_col);
                                if (Objects.equals(wall_collision, "H")) {
                                    board.get(white_row).set(white_col, " ");
                                    break;
                                }
                                after_collision(wall_collision, board);
                                board.get(white_row - 1).set(white_col, "*");
                            } catch (IndexOutOfBoundsException e) {
                                String wall_collision = board.get(board.size() - 1).get(white_col);
                                if (Objects.equals(wall_collision, "H")) {
                                    board.get(white_row).set(white_col, " ");
                                    break;
                                }
                                after_collision(wall_collision, board);
                                board.get(board.size() - 1).set(white_col, "*");
                            }
                        } else if (Objects.equals(next_place, "H")) {
                            board.get(white_row).set(white_col, " ");
                            break;
                        } else {
                            after_collision(next_place, board);
                            board.get(white_row + 1).set(white_col, "*");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        String next_place = board.get(0).get(white_col);
                        if (Objects.equals(next_place, "W")) {
                            String wall_collision = board.get(board.size() - 1).get(white_col);
                            if (Objects.equals(wall_collision, "H")) {
                                board.get(white_row).set(white_col, " ");
                                break;
                            }
                            after_collision(wall_collision, board);
                            board.get(board.size() - 1).set(white_col, "*");
                        } else if (Objects.equals(next_place, "H")) {
                            board.get(white_row).set(white_col, " ");
                            break;
                        } else {
                            after_collision(next_place, board);
                            board.get(0).set(white_col, "*");
                        }
                    }
                }
            }
            // after the moves, print the board, move and  score to output file
            write_output("Your movement is: \n");
            write_output(move_output + "\n\n");
            write_output("Your output is: \n");
            board.forEach(row -> {
                for (Object cell : row) {
                    write_output(cell + " ");
                }
                write_output("\n");
            });
            write_output("\n");
            write_output("Game Over! \n");
            write_output("Score: " + score);
        }
    }
}
