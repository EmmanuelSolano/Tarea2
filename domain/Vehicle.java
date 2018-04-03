/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Emmanuel
 */
public class Vehicle {
    //atributos
    private String name;
    private int age;
    private float mileage;
    private boolean american;
    private int series;

    
    //constructores
    public Vehicle(){
        this.name = "";
        this.age = 0;
        this.mileage = 0;
        this.american = false;
        this.series = 0;
    }//end contructor
    
    public Vehicle(String name, int age, float mileage, boolean american, int series) {
        this.name = name;
        this.age = age;
        this.mileage = mileage;
        this.american = american;
        this.series = series;
    }//end contructor
    
    //accesorios
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

    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public boolean isAmerican() {
        return american;
    }

    public void setAmerican(boolean american) {
        this.american = american;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }
    
    @Override
    public String toString() {
        return "Vehicle{" + "name=" + name + ", age=" + age + ", mileage=" + mileage + ", american=" + american + ", series=" + series + '}';
    }
    
    //method return the size on bytes of the characteristics
    public int sizeInBytes(){
        //retornar la suma en bytes de todos los atributos
        return 8 + this.getName().length() * 2;//en java hay una jerarquia de operadores la principal es la *
    }
    
    
}
