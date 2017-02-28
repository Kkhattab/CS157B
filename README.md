# CS157
157B

#Installation 
---

1. Download Homebrew (Mac Only) by entering this command into the terminal`/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

2. Type into terminal `brew install mysql`

3. We all have java but if you don't you can install it through home brew as well. `brew cask install java`

4. You can then use the MySQL commands through terminal to connect... which is how I am doing it at the moment.

#MySQL
---

1. Open terminal window and type `mysql -u root`

2. Type `create database hc;` 

3. To verify that you created the hc database type `show databases;` 

4. Use the newly created database by typing `use hc;`

5. Then set the source of the database to the hc.sql file. Enter `source YOUR_PATH/.../.../hc.sql`. 

6. Confirm the source has been set by viewing the tables of the database by entering `show tables;`. 

| Tables_in_hc  |
| ------------- |
| appointment   | 
| doctor        | 
| patient       |
| schedule      | 

7. To view the structure of our tables you can use the `desc TABLE_NAME;` command. 

desc doctor;

| Field      | Type         | Null | Key | Default | Extra          |
| -----------|--------------|------|-----|---------|----------------|
| id         | int(11)      | NO   | PRI | NULL    | auto_increment |
| first_name | varchar(255) | NO   |     | NULL    |                |
| last_name  | varchar(255) | NO   |     | NULL    |                |
| phone      | varchar(255) | NO   |     | NULL    |                |


desc patient;

| Field      | Type         | Null | Key | Default | Extra          |
|------------|--------------|------|-----|---------|----------------|
| id         | int(11)      | NO   | PRI | NULL    | auto_increment |
| first_name | varchar(255) | NO   |     | NULL    |                |
| last_name  | varchar(255) | NO   |     | NULL    |                |
| phone      | varchar(255) | YES  |     | NULL    |                |
| address    | varchar(255) | NO   |     | NULL    |                |
| symptom    | varchar(255) | NO   |     | NULL    |                |

desc schedule; 

| Field       | Type                                | Null | Key | Default | Extra          |
|-------------|-------------------------------------|------|-----|---------|----------------|
| id          | int(11)                             | NO   | PRI | NULL    | auto_increment |
| doctor_id   | int(11)                             | NO   |     | NULL    |                |
| day_of_week | enum('Mon','Tue','Wed','Thu','Fri') | NO   |     | NULL    |                |
| start_time  | time                                | NO   |     | NULL    |                |
| end_time    | time                                | NO   |     | NULL    |                |


desc appointment;

| Field       | Type                                  | Null | Key | Default | Extra          |
|-------------|---------------------------------------|------|-----|---------|----------------|
| id          | int(11)                               | NO   | PRI | NULL    | auto_increment |
| schedule_id | int(11)                               | NO   | MUL | NULL    |                |
| patient_id  | int(11)                               | NO   |     | NULL    |                |
| status      | enum('Booked','Cancelled','Complete') | YES  |     | NULL    |                |



#Java
---

##Formatting

- % - placeholder of an object
- 10 - length of the string or int or double
- s- type of object. s - string, d - int , f - floats/double
- \n insert a new line at end

##Prepared Statements 

The questiong mark in prepared statements is used to most importantly prevent sql injections. We don't trust any input from the user. 
`?` tells jdbc to pass the value we provide as is and should not treated as keywords or anything that might alter the query we wanted to
run.

##Joins

For appointments, the query must return the left side even if the right side is not there. The right side comes out as nulls. 

If you look at the query you see we use a key word called COALESCE which returns the 1st non-null value.

When we join with appointments and non exists we substitute that with 'Open'. This is called an left join or left outer join.

We use an inner join to list patients appointment because an appointment must exist and it must belong to schedule and also a patient. 

The schedule then must belong to a doctor. Here everything exists if we approach it from customer perspective.  

From doctors perspective, in his schedule there are times when he is not booked and hence no appoinment. 

If we want to get the entire schedule plus appointments we must do a left join where the doctor is on the left side to see if doctor is booked or not.

In show appointments function we are quering doctors appointments so we inner join appointment, schedule, doctor and patient.

