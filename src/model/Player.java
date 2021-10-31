package model;

public class Player {

	private String name;
	private int age;
	private String team;
	private double points;
	private double rebounds;
	private double assists;
	private double steals;
	private double blocks;
	private int key;

	public Player(String name, int age, String team, double points, double rebounds, double assists, double steals, double blocks) {
		this.name = name;
		this.age = age;
		this.team = team;
		this.points = points;
		this.rebounds = rebounds;
		this.assists = assists;
		this.steals = steals;
		this.blocks = blocks;
	}

	public Player(String[] attributes) {
		try {
			name = attributes[0];
			age = Integer.parseInt(attributes[1]);
			team = attributes[2];
			points = Double.parseDouble(attributes[3]);
			rebounds = Double.parseDouble(attributes[4]);
			assists = Double.parseDouble(attributes[5]);
			steals = Double.parseDouble(attributes[6]);
			blocks = Double.parseDouble(attributes[7]);
		} catch (NumberFormatException ignored) {}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public double getRebounds() {
		return rebounds;
	}

	public void setRebounds(double rebounds) {
		this.rebounds = rebounds;
	}

	public double getAssists() {
		return assists;
	}

	public void setAssists(double assists) {
		this.assists = assists;
	}

	public double getSteals() {
		return steals;
	}

	public void setSteals(double steals) {
		this.steals = steals;
	}

	public double getBlocks() {
		return blocks;
	}

	public void setBlocks(double blocks) {
		this.blocks = blocks;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getInfoWithSeparator(final String s) {
		return name + s + age + s + team + s + points + s + rebounds + s + assists + s + steals + s + blocks;
	}
}