/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import domain.Vehicle;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Emmanuel
 */
public class VehicleFile {
    
    //atributos
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;
    private int regSize=40;//20 characters with max for the name//max size of the registre of each file
    private String myFilePath;
    
    //constructor
    public VehicleFile(File file) throws IOException{
      
        //validation about the existance of the file
        if(file.exists() && !file.isFile()){
            throw  new IOException(file.getName() + "is a invalid file");
        }else{
            //make a new instance of RAF
            randomAccessFile = new RandomAccessFile(file, "rw");
            
            
            //when register have the file
            this.regsQuantity = (int)Math.ceil((double)randomAccessFile.length() / (double)this.regSize);//ceil redondea numero hacia arriba
        }
    }//end method
    public void cerrar() throws IOException{
        randomAccessFile.close();
    }
   
    //method to add vehicles in the file on the specific position
    public boolean addVehicle(int position, Vehicle addToVehicle) throws IOException{
        //validation of position
        
        if(position >= 0 && position <= this.regsQuantity){
            //check with the size is correct
            if(addToVehicle.sizeInBytes() > this.regSize || searchExist(addToVehicle)==true ){
                if(searchExist(addToVehicle)){
                    System.err.println("1004 - car was save before");
                }else{
                    System.err.println("1002 - record size is too large");
                }
                return false;
            }else{
                //write file
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(addToVehicle.getName());
                randomAccessFile.writeInt(addToVehicle.getAge());
                randomAccessFile.writeFloat(addToVehicle.getMileage());
                randomAccessFile.writeBoolean(addToVehicle.isAmerican());
                randomAccessFile.writeInt(addToVehicle.getSeries());
                //cerrar();
                return true;
            }
        }else{
            System.err.println("1001 - position is out of bounds");
            return false;
        }
        
    }//end method addVehicle
    
    
    
    //method to search if exist one vehicle before this
    public boolean searchExist(Vehicle vehicleSearch) throws IOException{
        Vehicle vehicleTemp;
        
        if(!vehicleSearch.getName().equalsIgnoreCase("delete")){
            for(int i = 0; i<this.regsQuantity;i++){
                vehicleTemp = this.getCar(i);
                if(vehicleTemp.getSeries()==vehicleSearch.getSeries()){
                    
                        return true;
                }
            }
        }
        return false;
        
    }
    
    
    
    //method to insert in end of the file
    public boolean addEndRecord(Vehicle car) throws IOException{
        boolean success = addVehicle(this.regsQuantity, car);
        
        if(success){
            ++this.regsQuantity;
        }
        return success;
    }//end method
    
    //get a vehicle in specific position
    public Vehicle getCar(int position) throws IOException{
        //validation position
        if(position >= 0 && position <= this.regsQuantity){
            randomAccessFile.seek(position * this.regSize);
            //read
            
            Vehicle vehicleTemp = new Vehicle();
            vehicleTemp.setName(randomAccessFile.readUTF());
            vehicleTemp.setAge(randomAccessFile.readInt());
            vehicleTemp.setMileage(randomAccessFile.readFloat());
            vehicleTemp.setAmerican(randomAccessFile.readBoolean());
            vehicleTemp.setSeries(randomAccessFile.readInt());
            //
            ///cerrar();
            if(vehicleTemp.getName().equals("delete")){
                
                return null;
            }else{
                
                return vehicleTemp;
                
            }
        }else{
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method
    
    //method to return the vehicle within of the file
    public ArrayList<Vehicle> getVehicleList() throws IOException{
        //make one instance of array list
        ArrayList<Vehicle> arrayListOfVehicles = new ArrayList<>();
        
        //browse the array to insert in the list
        for(int i = 0;i<this.regsQuantity;i++){
            Vehicle vehicleTemp = this.getCar(i);
            
            //insert car in the list
            if(vehicleTemp != null){
                
                arrayListOfVehicles.add(vehicleTemp);
            }
        }
        return arrayListOfVehicles;
    }//end method
    
    public boolean deleteVehicle(int series) throws IOException{
        Vehicle vehicleTemp;
      
        for(int i = 0; i<this.regsQuantity;i++){
            vehicleTemp = this.getCar(i);
            if(vehicleTemp.getSeries()==series){
                vehicleTemp.setName("delete");
                return this.addVehicle(i, vehicleTemp);
            }
        }
        
        return false;
    }//end method
    
    //method to update some dat of car
    public void updateCar(Vehicle addToVehicle, int series) throws IOException{
        Vehicle carUpdateTemp;
        
        for(int i=0;i<this.regsQuantity;i++){
            carUpdateTemp = this.getCar(i);
            if(carUpdateTemp.getSeries()==series){
                
                randomAccessFile.seek(i * this.regSize);
                randomAccessFile.writeUTF(addToVehicle.getName());
                randomAccessFile.writeInt(addToVehicle.getAge());
                randomAccessFile.writeFloat(addToVehicle.getMileage());
                randomAccessFile.writeBoolean(addToVehicle.isAmerican());
                randomAccessFile.writeInt(addToVehicle.getSeries());
                
            }
            
        }
        //cerrar();
    }//end method
    //me trae todos los registros menos el delete
    public ArrayList comprimeFileGetReg() throws IOException{
        ArrayList<Vehicle> arrayListOfVehicles = new ArrayList<>();
        
        //browse the array to insert in the list
        for(int i = 0;i<this.regsQuantity;i++){
            Vehicle vehicleTemp = this.getCar(i);
            
            //insert car in the list
            if((vehicleTemp != null)){
                if((vehicleTemp.getName()!="delete")){
                    arrayListOfVehicles.add(vehicleTemp);
                }
                
            }
        }
        
        return arrayListOfVehicles;
    }
    //Muestra los datos de un array
    public String muestraArreglo(ArrayList<Vehicle>array){
        String salida="Arreglo guarda registros: ";
        Vehicle temp;
        for(int i = 0;i<array.size();i++){
            temp=array.get(i);
            salida+="\n"+temp.getName()+"  "+temp.getAge()+"  "+temp.getMileage()+"  "+temp.isAmerican()+"  "+temp.getSeries();
        }
        return salida;
    }
    
    public void compresFile(){
        this.regsQuantity=0;
    }
    
    public int cantidad(){
        return this.regsQuantity;
    }
}
