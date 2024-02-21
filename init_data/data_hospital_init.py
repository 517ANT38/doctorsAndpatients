from datetime import datetime, timedelta
import requests
import json
from random import randint

BASE_URL = "http://localhost:8080/api/"

FAMILIES = ["Ivanov","Petrov","Zaizev","Tashchilin","Dyrakov"]
NAMES = ["Anton","Peter","Michail","Victor","Ivan"]
PATRONUMIC = ["Alexsandrovich","Petrovich","Ivanovich","Victorovich","Michailovich"]
JOBS_DOCTORS = ["Therapist", "Ophthalmologist", "Gasteniterologist","Neurologist","Cardiologist"]
HEADERS= {"Content-Type":"application/json; charset=utf-8","accept":"*/*"}
num_pass_arr = []
snils_arr = []

for i in range(20):
    index = randint(0,4)
    fio = {
        "family":FAMILIES[index],
        "name":NAMES[index],
        "patronymic":PATRONUMIC[index]
    }
    tmp_snils = randint(10**11,10**11+10**10) + i
    snils_arr.append(tmp_snils)
    dt = datetime.now() - timedelta(days=randint(1,1000))
    dtb = datetime.now() - timedelta(weeks=randint(10**2,10**3))
    dto = {
        "fio":fio,
        "jobTitle": JOBS_DOCTORS[index],
        "snils": f"{tmp_snils}",
        "dateBirth":dtb.strftime("%Y-%m-%d"),
        "regHospital": dt.strftime("%Y-%m-%d")
    }
    print(requests.post(BASE_URL+"patients/addPatient",data=json.dumps(dto), headers=HEADERS).text)


for i in range(20):
    index = randint(0,4)
    fio = {
        "family":FAMILIES[index],
        "name":NAMES[index],
        "patronymic":PATRONUMIC[index]
    }
    tmp_pass = randint(1000,9998) + randint(1000,9998) + i
    num_pass_arr.append(tmp_pass)
    dt = datetime.now() - timedelta(days=randint(1,1000))
    dto = {
        "fio":fio,
        "jobTitle": JOBS_DOCTORS[index],
        "numPass": tmp_pass,
        "dateEmp": dt.strftime("%Y-%m-%d")
    }
    print(requests.post(BASE_URL+"doctors/addDoctor",data=json.dumps(dto),headers=HEADERS).text)


for i in range(300):
    snils = randint(0,len(snils_arr)-1)
    numPass = randint(0,len(num_pass_arr)-1)
    dt = datetime.now() + timedelta(days=randint(1,1000),hours=6+i)
    dto={
            "numPass":num_pass_arr[numPass],
            "snils": snils_arr[snils],
            "date": dt.strftime("%Y-%m-%d %H:%m")
        }
    print(requests.post(BASE_URL+"patients/addNoteInDoctor",data=json.dumps(dto),headers=HEADERS).text)