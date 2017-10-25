# KeystrokeIdentification

As a part of Graduate Research Assistantship

Task: Identify an individual based on keystroke timestamping. 

1. Profile has been created based on user keystroke timings - time lapse between key release and key pressed.
2. Calculated averages for each characters (unigraph) and the pairs (digraph)
3. Calculated Standard deviation for unigraph and digraph.
4. Profiles are created with first name and last name of an user.
5. Sample text will be provided without providing user details. These keystroke values will be verified with the existing profiles and the related profile will be displayed as identified person.
6. Inorder to identify an individual, I have calculated distance between two characters and pairs using 4 different algorithms.
  a. Scalable Euclidean 
  b. Scalable Manhattan
  c. Absolute
  d. Relative
7. Every algorithm produces two ouputs - unigraph and digraph.
8. A sample data will be compared with each profile in the system and all algorithms will be calculated with each profile.
9. Maximum value among a profile and sample combination will be calculated. 
10. Among 8 outputs of each algorithms, top three usernames will be displayed as identified person. To make it more accurate we can provide huge data while creating a profile and display top one username.

PS: The mathematical part of this project has been extracted from Dr. Gunetti's research work on Keystoke Analysis.
