import java.util.concurrent.*;

public class Fibonacci extends RecursiveTask<Integer> {

	public static void main(String[] args) {
		int fiboOf = Integer.parseInt(args[0]);
		int extime = Integer.parseInt(args[1]);

		while (extime-- > 0) {
			ForkJoinPool fjpool = new ForkJoinPool();
			ForkJoinTask<Integer> task = new Fibonacci(fiboOf);
			long starttime = System.nanoTime();
			int runNumber = Integer.parseInt(args[1])-extime;
			
			System.out.println(String.format("On run # %d: Fib(%d) = %d ", runNumber, fiboOf, fjpool.invoke(task)));
			System.out.println(String.format("Time took: %f [msec]", (System.nanoTime() - starttime) / 1000000.0));
			System.out.println();
		}
	}

	private final int n;
	public Fibonacci(int n) { this.n = n; }

	public Integer compute() {
		if (n <= 1) return n;
		Fibonacci f1 = new Fibonacci(n - 1);
		f1.fork();
		Fibonacci f2 = new Fibonacci(n - 2);
		return f2.compute() + f1.join();
	}
}
