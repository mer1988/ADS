
r  = open("input_5000_1_part1.txt", "r")
w  = open("new_input_5000_1_part1.txt", "w")

for l in r:
	w.write(l.replace("\r", ""))

w.close()
r.close()