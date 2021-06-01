package com.covid19.data;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.covid19.entities.Admin;
import com.covid19.entities.CovidTest;
import com.covid19.entities.Hospital;
import com.covid19.entities.HospitalType;
import com.covid19.entities.HospitalZone;
import com.covid19.entities.Patient;
import com.covid19.entities.Status;
import com.covid19.security.CryptWithMD5;
import com.covid19.utilities.RandomNameString;

public class CovidData {
	public void addPatientStatus(EntityManager entityManager,int[] patientId)
	{
		LocalDate startDate = LocalDate.of(2021, 1, 1); //start date
	    long start = startDate.toEpochDay();

	    LocalDate endDate = LocalDate.of(2021,12 , 31); //end date
	    long end = endDate.toEpochDay();
		for(int i=0;i<patientId.length/2;i++)
		{
			Status status=new Status();
			long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
			System.out.println(LocalDate.ofEpochDay(randomEpochDay));
			status.setPatient(entityManager.find(Patient.class, patientId[i]));
			status.setConfirmDate(LocalDate.ofEpochDay(randomEpochDay));
			status.setIsolationDate(status.getConfirmDate().plusDays(2));
			status.setRecoveredDate(status.getIsolationDate().plusDays(20));
			entityManager.getTransaction().begin();
			entityManager.persist(status);
			entityManager.getTransaction().commit();
			
		}
		for(int i=patientId.length/2;i<((patientId.length/2)+(patientId.length/4));i++)
		{
			Status status=new Status();
			long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
			System.out.println(LocalDate.ofEpochDay(randomEpochDay));
			status.setPatient(entityManager.find(Patient.class, patientId[i]));
			status.setConfirmDate(LocalDate.ofEpochDay(randomEpochDay));
			status.setIsolationDate(status.getConfirmDate().plusDays(2));
			status.setDeathDate(status.getIsolationDate().plusDays(10));
			entityManager.getTransaction().begin();
			entityManager.persist(status);
			entityManager.getTransaction().commit();
			
		}
		for(int i=((patientId.length/2)+(patientId.length/4));i<patientId.length;i++)
		{
			Status status=new Status();
			long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
			System.out.println(LocalDate.ofEpochDay(randomEpochDay));
			status.setPatient(entityManager.find(Patient.class, patientId[i]));
			status.setConfirmDate(LocalDate.ofEpochDay(randomEpochDay));
			status.setIsolationDate(status.getConfirmDate().plusDays(1));
			entityManager.getTransaction().begin();
			entityManager.persist(status);
			entityManager.getTransaction().commit();
			
		}
	}
	
	public void addAdmin(EntityManager entityManager) throws NoSuchAlgorithmException
	{
		List<Hospital> hospitals=new ArrayList<Hospital>();
		Admin admin=new Admin();
		admin.setAdminEmailId("testadmin@gmail.com");
		admin.setAdminFirstName("Surajnarayan");
		admin.setAdminLastName("Raut");
		admin.setAdminPassword(CryptWithMD5.cryptWithMD5("testpassword"));
		admin.setAdminUserName("TestAdmin123");
		admin.setHospitals(hospitals);
		entityManager.getTransaction().begin();
		entityManager.persist(admin);
		entityManager.getTransaction().commit();
		adminId=admin.getAdminId();
	}
	public static int adminId=1;
	public int[]  typeData(EntityManager manager)
	{
		String []types= {"Government","Private"};
		int typeId[]=new int[types.length];
		for(int i=0;i<types.length;i++)
		{
			HospitalType hospitalType=new HospitalType();
			hospitalType.setTypeName(types[i]);
			manager.getTransaction().begin();
			manager.persist(hospitalType);
			manager.getTransaction().commit();
			typeId[i]=hospitalType.getTypeId();
		}
		return typeId;
	}
	public int[] zoneData(EntityManager manager)
	{
		String zones[] = { "Andhra Pradesh",
		                "Arunachal Pradesh",
		                "Assam",
		                "Bihar",
		                "Chhattisgarh",
		                "Goa",
		                "Gujarat",
		                "Haryana",
		                "Himachal Pradesh",
		                "Jammu and Kashmir",
		                "Jharkhand",
		                "Karnataka",
		                "Kerala",
		                "Madhya Pradesh",
		                "Maharashtra",
		                "Manipur",
		                "Meghalaya",
		                "Mizoram",
		                "Nagaland",
		                "Odisha",
		                "Punjab",
		                "Rajasthan",
		                "Sikkim",
		                "Tamil Nadu",
		                "Telangana",
		                "Tripura",
		                "Uttarakhand",
		                "Uttar Pradesh",
		                "West Bengal",
		                "Andaman and Nicobar Islands",
		                "Chandigarh",
		                "Dadra and Nagar Haveli",
		                "Daman and Diu",
		                "Delhi",
		                "Lakshadweep",
		                "Puducherry"};
		int[] zoneId=new int[zones.length];
		for (int i=0;i<zones.length;i++) {
			HospitalZone zone=new HospitalZone();
			zone.setZoneName(zones[i]);
			manager.getTransaction().begin();
			manager.persist(zone);
			manager.getTransaction().commit();
			zoneId[i]=zone.getZoneId();
		}
		return zoneId;
	}
public int[] hospitalData(EntityManager manager,int zoneId[],int typeId[])
{
	 Random rand = new Random();
	 int[] hospitalId=new int[30];
for(int i=0;i<30;i++)
{
	Hospital hospital=new Hospital();
	hospital.setHospitalName(RandomNameString.getAlphaNumericString(8)+" "+RandomNameString.getAlphaNumericString(8));
	hospital.setHospitalICUBed(rand.nextInt(1000));
	hospital.setHospitalGeneralBed(rand.nextInt(1000));
	hospital.setHospitalType(manager.find(HospitalType.class, typeId[rand.nextInt(typeId.length)]));
	hospital.setHospitalZone(manager.find(HospitalZone.class, zoneId[rand.nextInt(zoneId.length)]));
	Admin admin=manager.find(Admin.class , adminId);
	admin.getHospitals().add(hospital);
	manager.getTransaction().begin();
	manager.persist(hospital);
	manager.persist(admin);
	manager.getTransaction().commit();
	System.out.println(hospital.getHospitalId());
	hospitalId[i]=hospital.getHospitalId();
}
return hospitalId;
	
}
public int[] patientData(EntityManager manager,int[] hospitalId)
{
	 Random rand = new Random();
	 int[] patientId=new int[200];
for(int i=0;i<200;i++)
{
	Patient patient=new Patient();
	patient.setPatientFirstName(RandomNameString.getAlphaNumericString(6));
	patient.setPatientLastName(RandomNameString.getAlphaNumericString(5));
	if(i<125)
		patient.setPatientGender("Male");
	else
		patient.setPatientGender("Female");
	patient.setPatientAge(rand.nextInt(100));
	List<Status> status=new ArrayList<>();
	List<CovidTest> covidTests=new ArrayList<>();
	patient.setStatus(status);
	patient.setTest(covidTests);
	patient.setHospital(manager.find(Hospital.class, hospitalId[rand.nextInt(hospitalId.length)]));
	manager.getTransaction().begin();
	manager.persist(patient);
	manager.getTransaction().commit();
patientId[i]=patient.getPatientId();
}
return patientId;
}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-PU");
		EntityManager em = factory.createEntityManager();
		CovidData data=new CovidData();
		data.addAdmin(em);
		int typeId[]=data.typeData(em);
		int zoneId[]=data.zoneData(em);
		int hospitalId[]=data.hospitalData(em, zoneId, typeId);
		int patientId[]=data.patientData(em, hospitalId);
		data.addPatientStatus(em, patientId);
	}

}
