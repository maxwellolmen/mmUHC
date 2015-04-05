package mcm.Maxxwell.mmUHC;

public class BorderCounter extends Thread {
	private Arena arena;
	
	public BorderCounter(Arena arena) {
		this.arena = arena;
	}
	
	public void run() {
		try {
			Thread.sleep(3600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int size = (int) Math.round(arena.getWorld().getWorldBorder().getSize());
		if (!(size <= 10)) {
			arena.getWorld().getWorldBorder().setSize(size - 2);
		}
	}
}
