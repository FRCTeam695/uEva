import array as arr

import tkinter
import tkinter.font

import time
import sys

from networktables import NetworkTables

from tkinter import *

import networktables

import ntcore;

def changeColorRed():
    colorRed.configure(fg = "red", bg = "white")
    colorTable.putString("colorPicked", "red")
    print("red to color table")
    time.sleep(1)
    colorRed.configure(fg = "white", bg = "red")
    print("the color you chose is red")

def changeColorBlue():
    colorBlue.configure(fg = "blue", bg = "white")
    colorTable.putString("colorPicked", "blue")
    print("blue to color table")
    time.sleep(1)
    colorBlue.configure(fg = "white", bg = "blue")
    print("the color you chose is blue")

def changeColorGreen():
    colorGreen.configure(fg = "green", bg = "white")
    colorTable.putString("colorPicked", "green")
    print("blue to color table")
    time.sleep(1)
    colorGreen.configure(fg = "white", bg = "green")
    print("the color you chose is green")

# set the roborio IP address
ip = "10.6.95.2"
NetworkTables.initialize(server=ip)
time.sleep(1)

if NetworkTables.isConnected():
    print("Connected to NetworkTables server")
else:
    print("Failed to connect to NetworkTables server")

colorTable = NetworkTables.getTable("color")

colorTable.putString("colorPicked", "none")

# new interface window
window = tkinter.Tk()
window.title("LED Color")
window.geometry('574x574')

# button 1
# alternative: 
# colorRed = tk.Button(window, text="RED", width=20, height=2, fg="white", bg="red", font=font.Font(size=14, weight="bold"), command=changeColorRed)
# colorRed.place(x=160, y=100)
colorRed = tkinter.Button(window)
colorRed['width'] = 20
colorRed['height'] = 2
colorRed['fg'] = "white"
colorRed['bg'] = "red"
colorRed['text'] = "RED"
colorRed['font'] = tkinter.font.Font(size = 14, weight = "bold")
colorRed['command'] = changeColorRed
colorRed.place(x=160, y=100)

# button 2
# alternative: 
# colorBlue = tk.Button(window, text="BLUE", width=20, height=2, fg="white", bg="blue", font=font.Font(size=14, weight="bold"), command=changeColorBlue)
# colorRed.place(x=160, y=300)
colorBlue = tkinter.Button(window)
colorBlue['width'] = 20
colorBlue['height'] = 2
colorBlue['fg'] = "white"
colorBlue['bg'] = "blue"
colorBlue['text'] = "BLUE"
colorBlue['font'] = tkinter.font.Font(size = 14, weight = "bold")
colorBlue['command'] = changeColorBlue
colorBlue.place(x=160, y=300)

# button 3
# alternative: 
# colorGreen = tk.Button(window, text="GREEN", width=20, height=2, fg="white", bg="green", font=font.Font(size=14, weight="bold"), command=changeColorGreen)
# colorGreen.place(x=160, y=200)
colorGreen = tkinter.Button(window)
colorGreen['width'] = 20
colorGreen['height'] = 2
colorGreen['fg'] = "white"
colorGreen['bg'] = "green"
colorGreen['text'] = "GREEN"
colorGreen['font'] = tkinter.font.Font(size = 14, weight = "bold")
colorGreen['command'] = changeColorGreen
colorGreen.place(x=160, y=200)

# GUI
window.mainloop()



