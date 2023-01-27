package tijdtools;

public record Tijd(
		int uur,
		int minuut,
		int seconde,
		)
		{

	public void increment(Tijd step){
		this.seconde += step.seconde;
		this.minuut += step.minuut;
		this.uur += step.uur;
		if (this.seconde>=60){
			this.seconde-=60;
			this.minuut++;
		}
		if (this.minuut>=60){
			this.minuut-=60;
			this.uur++;
		}
	}

	public Tijd copyTijd(){
		return new Tijd(this.uur, this.minuut, this.seconde);
	}
	
}
