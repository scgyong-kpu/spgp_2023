import random

bpm = 122
start = 5500
end = 70000

mspb = 60000 / bpm * 2

def main():
  time = start
  while time < end:
    printNote(time)
    split = random.randrange(0, 2) == 0
    if split:
      printNote(time + mspb / 2)
    time += mspb

def printNote(t):
  msec = round(t)
  lane = random.randrange(0, 5)
  print(f'N {lane} {msec}')

main()



