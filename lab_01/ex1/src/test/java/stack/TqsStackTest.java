package stack;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

public class TqsStackTest {



        @Test
        @DisplayName("A stack is empty on construction")
        public void testEmptyOnConstruction() {
            Stack<String> stack = new Stack<>();
            assertTrue(stack.isEmpty());
            assertEquals(0, stack.size());
        }

        @Test
        @DisplayName("A stack has size 0 on construction")
        public void testSizeZeroOnConstruction() {
            Stack<String> stack = new Stack<>();
            assertEquals(0, stack.size());
        }

        @Test
        @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
        public void testPushNotEmpty() {
            Stack<String> stack = new Stack<>();
            stack.push("a");
            assertFalse(stack.isEmpty());
            assertEquals(1, stack.size());
        }

        @Test
        @DisplayName("If one pushes x then pops, the value popped is x")
        public void testPushPop() {
            Stack<String> stack = new Stack<>();
            stack.push("a");
            String item = stack.pop();
            assertEquals("a", item);
            assertTrue(stack.isEmpty());
            assertEquals(0, stack.size());
        }

        @Test
        @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
        public void testPushPeek() {
            Stack<String> stack = new Stack<>();
            stack.push("a");
            String item = stack.peek();
            assertEquals("a", item);
            assertFalse(stack.isEmpty());
            assertEquals(1, stack.size());
        }

        @Test
        @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
        public void testPopAll() {
            Stack<String> stack = new Stack<>();
            stack.push("a");
            stack.push("b");
            stack.pop();
            stack.pop();
            assertTrue(stack.isEmpty());
            assertEquals(0, stack.size());
        }

        @Test
        @DisplayName("Popping from an empty stack throws a NoSuchElementException")
        public void testPopEmpty() {
            Stack<String> stack = new Stack<>();
            assertThrows(NoSuchElementException.class, () -> {
                stack.pop();
            });
        }

        @Test
        @DisplayName("Peeking into an empty stack throws a NoSuchElementException")
        public void testPeekEmpty() {
            Stack<String> stack = new Stack<>();
            assertThrows(NoSuchElementException.class, () -> {
                stack.peek();
            });
        }

    }


