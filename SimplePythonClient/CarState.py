'''
This class is based on the C++ code of Daniele Loiacono

Created on  05.05.2011
@author: Thomas Fischle
@contact: fisch27@gmx.de
'''

import SimplePythonClient.SimpleParser as SimpleParser 

FOCUS_SENSORS_NUM = 5
TRACK_SENSORS_NUM = 19
OPPONENTS_SENSORS_NUM = 36


class CarState(object):
    
    def __init__(self, sensors):
        self.angle = 0.0
        self.curLapTime = 0.0
        self.damage = 0.0
        self.__distFromStart = 0.0
        self.distRaced = 0.0
        #focus[FOCUS_SENSORS_NUM] = 0.0
        self.focus=[]
        self.fuel = 0.0
        self.gear = 0
        self.lastLapTime = 0.0
        #opponents[OPPONENTS_SENSORS_NUM] = 0.0
        self.opponents=[]
        self.racePos = 0
        self.rpm = 0
        self.speedX = 0.0
        self.speedY = 0.0
        self.speedZ = 0.0
        #track[TRACK_SENSORS_NUM] = 0.0
        self.track=[]
        self.trackPos = 0.0
        #wheelSpinVel[4] = 0.0
        self.wheelSpinVel=[]
        self.z = 0.0
        
        sp = SimpleParser.SimpleParser()
        self.angle = sp.parse(sensors, "angle")
        self.curLapTime = sp.parse(sensors, "curLapTime")
        self.damage = sp.parse(sensors, "damage")
        self.__distFromStart = sp.parse(sensors, "distFromStart")
        self.distRaced = sp.parse(sensors, "distRaced")
        self.focus = sp.parse(sensors, "focus")
        self.fuel = sp.parse(sensors, "fuel")
        self.gear = sp.parse(sensors, "gear")
        self.lastLapTime = sp.parse(sensors, "lastLapTime")
        self.opponents = sp.parse(sensors, "opponents")
        self.racePos = sp.parse(sensors, "racePos")
        self.rpm = sp.parse(sensors, "rpm")
        self.speedX = sp.parse(sensors, "speedX")
        self.speedY = sp.parse(sensors, "speedY")
        self.speedZ = sp.parse(sensors, "speedZ")
        self.track = sp.parse(sensors, "track")
        self.trackPos = sp.parse(sensors, "trackPos")
        self.wheelSpinVel = sp.parse(sensors, "wheelSpinVel")
        self.z = sp.parse(sensors, "z")

    def __str__(self):
        sp = SimpleParser.SimpleParser()
        str = ""
        str  = sp.stringify("angle", self.angle)
        str += sp.stringify("curLapTime", self.curLapTime)
        str += sp.stringify("damage", self.damage)
        str += sp.stringify("distFromStart", self.__distFromStart)
        str += sp.stringify("distRaced", self.distRaced)
        str += sp.stringify("focus", self.focus)
        str += sp.stringify("fuel", self.fuel)
        str += sp.stringify("gear", self.gear)
        str += sp.stringify("lastLapTime", self.lastLapTime)
        str += sp.stringify("opponents", self.opponents)
        str += sp.stringify("racePos", self.racePos)
        str += sp.stringify("rpm", self.rpm)
        str += sp.stringify("speedX", self.speedX)
        str += sp.stringify("speedY", self.speedY)
        str += sp.stringify("speedZ", self.speedZ)
        str += sp.stringify("track", self.track)
        str += sp.stringify("trackPos", self.trackPos)
        str += sp.stringify("wheelSpinVel", self.wheelSpinVel)
        str += sp.stringify("z", self.z)
      
        return str
            

    def getAngle(self):
        #SimpleParser returns a list
        return self.angle[0]
    
     
    def setAngle(self, angle):
        self.angle = angle  
    
     
    def getCurLapTime(self):
        return self.curLapTime[0]
    
     
    def setCurLapTime(self, curLapTime):
        self.curLapTime = curLapTime
    
    
    def getDamage(self):
        return self.damage[0]
    
    
    def setDamage(self, damage):
        self.damage = damage
    
    
    def getDistFromStart(self):
        return self.__distFromStart[0]
     
    
    def setDistFromStart(self, distFromStart):
        self.__distFromStart = distFromStart
    
    
    def getDistRaced(self):
        return self.distRaced[0]
      
    
    def setDistRaced(self, distRaced):
        self.distRaced = distRaced
    
    
    
    def getFocus(self, i):
        assert(i>=0 and i<FOCUS_SENSORS_NUM)
        return self.focus[i]
    
        
    def setFocus(self, i,  value):
        assert(i>=0 and i<FOCUS_SENSORS_NUM)
        self.focus[i] = value
      
    
    def getFuel(self):
        return self.fuel[0]
    
     
    def setFuel(self, fuel):
        self.fuel = fuel
    
    
    def getGear(self):
        return int(self.gear[0])
       
    
    def setGear(self, gear):
        self.gear = gear
       
     
    def getLastLapTime(self):
        return self.lastLapTime[0]
      
     
    def setLastLapTime(self, lastLapTime):
        self.lastLapTime = lastLapTime
     
    
    def getOpponents(self, i):
        assert(i>=0 and i<OPPONENTS_SENSORS_NUM)
        return self.opponents[i]
                
    
    def setOpponents(self, i,  value):
        assert(i>=0 and i<OPPONENTS_SENSORS_NUM)
        self.opponents[i] = value
    
    
    def getRacePos(self):   
        return self.racePos[0]
      
    
    def setRacePos(self, racePos):    
        self.racePos = racePos
    
    
    def getRpm(self):
        return self.rpm[0]

    
    def setRpm(self,  rpm):
        self.rpm = rpm
      
    
    def getSpeedX(self):
        return self.speedX[0]
       
    
    def setSpeedX(self,  speedX):
        self.speedX = speedX
       
    
    def getSpeedY(self):
        return self.speedY[0]
    
       
    def setSpeedY(self,  speedY):
        self.speedY = speedY
      
    
    def getSpeedZ(self):
        return self.speedZ[0]
      
    
    def setSpeedZ(self, speedZ):
        self.speedZ = speedZ
    
        
    def getTrack(self, i):
        assert(i>=0 and i<TRACK_SENSORS_NUM)
        return self.track[i]
    
    def getTracks(self):
        return self.track
          
    
    def setTrack(self, i,  value):
        assert(i>=0 and i<TRACK_SENSORS_NUM)
        self.track[i] = value
        
    
    def getTrackPos(self):
        return self.trackPos[0]
       
    
    def setTrackPos(self, trackPos):
        self.trackPos = trackPos
    
     
    def getWheelSpinVel(self, i):
        assert(i>=0 and i<4)
        return self.wheelSpinVel[i]
    
    
    def setWheelSpinVel(self, i,  value):
        assert(i>=0 and i<4)
        self.wheelSpinVel[i]=value
    
    def getZ(self):
            return self.z
    
    
    def setZ(self, z) :
        self.z = z

