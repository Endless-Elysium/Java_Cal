import java.util.*;

public class Main {
    private static final String NUMBERS = "0123456789";
    private static final String OPERATORS = "+-x/()";
    private static final Scanner ConsoleInput = new Scanner(System.in);
    public static void main(String[] args) {
        List<String> InputQueue = new ArrayList<>();
        Stack<String> OpStack = new Stack<>();
        String[] Input = ConsoleInput.nextLine().split(" ");
        for (String x : Input) {
            char[] y = new char[]{x.charAt(0)};
            String z = new String(y);
            if (NUMBERS.contains(z)) {
                System.out.println(x + " is a number");
                InputQueue.add(x);
            } else if (OPERATORS.contains(z)) {
                System.out.println(x + " is a operator");
                try {
                    if (x.equals("(") || OpStack.getLast().equals("(")) {
                        System.out.println("Last was para or is para");
                        OpStack.add(x);
                    } else if(x.equals(")")) {
                        System.out.println();
                        for (String a : OpStack) {
                            if (a.equals("(")) {
                                OpStack.removeLast();
                                break;
                            }
                            InputQueue.add(OpStack.getLast());
                            OpStack.removeLast();
                        }
                    } else if(!CompareOP(x,OpStack.getLast())) {
                        System.out.println(x + " < " + OpStack.getLast());
                        InputQueue.add(OpStack.getLast());
                        OpStack.removeLast();
                        OpStack.add(x);
                    } else {
                        System.out.println("There is nothing in OPStack");
                        OpStack.add(x);
                    }
                } catch (NoSuchElementException e) {
                    OpStack.add(x);
                }

            }
        }
        System.out.println();
        for (int x = OpStack.size(); x > 0; x--) {
            InputQueue.add(OpStack.get(x - 1));
        }
        for (String x : InputQueue) {
            System.out.print(x);
        }
        Stack<Double> NumberStack = new Stack<>();
        System.out.println("NumberStack: ");
        for (String x : InputQueue) {

            for (double y : NumberStack) {
                System.out.print( y + ", ");
            }
            System.out.println();
            if (NUMBERS.contains(x)) {
                NumberStack.push(Double.parseDouble(x));
            } else {
                try {
                    double a = NumberStack.pop();
                    double b = NumberStack.pop();
                    double c = switch (x) {
                        case "+" -> Add(b, a);
                        case "-" -> Minus(b, a);
                        default -> 0;
                    };
                    NumberStack.push(c);
                } catch (NoSuchElementException e) {
                    NumberStack.push(0.0);
                }

            }
        }
        double fresult = 0;
        for (double x : NumberStack) {
            fresult += x;
        }
        System.out.println("\n");
        System.out.println("Result: " + fresult);

    }
    private static int GetOrder(String s) {
        //Order = "ex/+-";
        String OrderSet1 = "+-";
        String OrderSet2 = "x/";
        String OrderSet3 = "e";

        if (OrderSet1.contains(s)) {
            return 1;
        } else if (OrderSet2.contains(s)) {
            return 2;
        } else if (OrderSet3.contains(s)) {
            return 3;
        }
        return 0;
    }
    private static boolean CompareOP(String o1, String o2) {

        String RightOrder = "e";
        boolean o1Right = RightOrder.contains(o1);
        boolean o2Right = RightOrder.contains(o2);
        int o1Place = GetOrder(o1);
        int o2Place = GetOrder(o2);

        if ((!o1Right && !o2Right) && (o1Place == o2Place)) {
            return false;
        }

        return o1Place < o2Place;
    }

    private static double Add(double x, double y) {return x + y;}
    private static double Minus(double x, double y) {return x - y;}
}
