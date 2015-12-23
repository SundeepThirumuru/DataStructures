import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: sthirumuru
 * Date: 16/12/15
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParenthesesMatch {


    public static void main(String[] args) {
        String exp = "(1 + ([(2))]";
        System.out.println("Input Expression " + exp);
        System.out.println(doParenthesesMatch(exp));
        System.out.println("Output Expression " + fixParenthesesMisMatch(exp));
    }

    public static Map<Character, Character> parenthesesMatchMap = new LinkedHashMap<Character, Character>();
    static
    {
        parenthesesMatchMap.put('(', ')');
        parenthesesMatchMap.put('[', ']');
        parenthesesMatchMap.put('{', '}');
    }

    public static Map<Character, Character> reverseParenthesesMatchMap = new LinkedHashMap<Character, Character>();
    static
    {
        reverseParenthesesMatchMap.put(')', '(');
        reverseParenthesesMatchMap.put(']', '[');
        reverseParenthesesMatchMap.put('}', '{');
    }

    public static boolean doParenthesesMatch(String exp)
    {
        Stack<Character> stack = new Stack<Character>();
        for(Character c : exp.toCharArray())
        {
            if(parenthesesMatchMap.containsKey(c))
            {
                stack.push(c);
            }
            else if(reverseParenthesesMatchMap.containsKey(c))
            {
                if(stack.isEmpty())
                {
                    return false;
                }
                else
                {
                    Character c2 = stack.pop();
                    if(!reverseParenthesesMatchMap.get(c).equals(c2))
                    {
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }

    public static String fixParenthesesMisMatch(String exp)
    {
        Stack<Character> stack = new Stack<Character>();
        Stack<Integer> indices = new Stack<Integer>();
        char[] charArray = exp.toCharArray();
        Set<Integer> toFixIndices = new LinkedHashSet<Integer>();
        for(int i=0; i<charArray.length; i++)
        {
            char c = charArray[i];
            if(parenthesesMatchMap.containsKey(c))
            {
                stack.push(c);
                indices.push(i);
            }
            else if(reverseParenthesesMatchMap.containsKey(c))
            {
                if(stack.isEmpty())
                {
                    toFixIndices.add(i);
                }
                else
                {
                    Character c2 = stack.pop();
                    if(!reverseParenthesesMatchMap.get(c).equals(c2))
                    {
                        toFixIndices.add(i);
                    }
                    else
                    {
                        indices.pop();
                    }
                }
            }
        }
        toFixIndices.addAll(indices);

        char[] newCharArray = new char[charArray.length - toFixIndices.size()];
        int j = 0;
        for(int i=0; i < charArray.length; i++)
        {
            if(!toFixIndices.contains(i))
            {
                newCharArray[j] = charArray[i];
                j++;
            }
        }
        return new String(newCharArray);
    }
}
