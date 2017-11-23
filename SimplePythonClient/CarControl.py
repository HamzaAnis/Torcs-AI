'''
This class is based on the C++ code of Daniele Loiacono

Created on  08.05.2011
@author: Thomas Fischle
@contact: fisch27@gmx.de
'''

import SimpleParser

class CarControl:

    # Accelerate command [0,1]
    accel = 0.0
    
    # Brake command [
    brake = 0.0
    
    # Gear command
    gear = 0
    
    # Steering command [-1,1]
    steer= 0.0
    
    # Clutch command [0,1]
    clutch= 0.0
    
    # meta-command
    meta = 0
    
    # focus command [-90,90], i.e. angle of track sensor focus desired by client
    focus = 0
    
    def __init__(self,  accel,  brake,  gear,  steer,  clutch,  focus,  meta = 0):
        self.accel = accel
        self.brake = brake
        self.gear  = gear
        self.steer = steer
        self.clutch = clutch
        self.focus = focus
        self.meta = meta
        
    def __str__(self):
        str = ""
        sp = SimpleParser.SimpleParser()
        str  = sp.stringify("accel", self.accel)
        str += sp.stringify("brake", self.brake)
        str += sp.stringify("gear",  self.gear)
        str += sp.stringify("steer", self.steer)
        str += sp.stringify("clutch", self.clutch)
        str += sp.stringify("focus",  self.focus)
        str += sp.stringify("meta", self.meta)
        return str    
        
    


