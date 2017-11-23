'''
This class is based on the C++ code of Daniele Loiacono

Created on  05.05.2011
@author: Thomas Fischle
@contact: fisch27@gmx.de
'''
import SimplePythonClient.CarControl as CarControl
import numpy as np

class tstage:
    WARMUP = 0
    QUALIFYING = 1
    RACE = 2
    UNKNOWN = 3


STUCKANGLE = np.math.pi/6
STUCKTIME = 1

class BaseDriver(object):

    #typedef enum{WARMUP,QUALIFYING,RACE,UNKNOWN} tstage;
    stage = tstage()  
    trackName=""
    gearUp = [5000,6000,6000,6500,7000,0]
    gearDown = [0,2500,3000,3000,3500,3500]
    #bringingCarBackOnStreet = False

    def __init__(self):
        print "init BaseDriver"
        self.stuckCounter = 0
        self.bringingCartBack = 0
        
    def init(self, angles):
        #Initialization of the desired angles for the rangefinders
        i = 0
        for i in range(0,len(angles)):
            angles[i]=-90+i*10    
    
    #The main function: 
    #     - the input variable sensors represents the current world sate
    #     - it returns a string representing the controlling action to perform    
    def Update(self, sensors):
        return sensors
    
    def getInitAngles(self):
        #return [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        return [-90,-80,-70,-60,-50,-40,-30,-20,-10, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90]


    #function called at shutdown
    def onShutdown(self):
        print "Bye bye!"
    
    #function called at server restart
    def onRestart(self):
        print "Restarting the race!"

    def getGear(self, sensors): 
        gear = sensors.getGear()
        rpm  = sensors.getRpm()

        # if gear is 0 (N) or -1 (R) just return 1 
        if gear < 1:
            return 1
        # check if the RPM value of car is greater than the one suggested 
        # to shift up the gear from the current one     
        if gear < 6 and rpm >= self.gearUp[gear-1]:
            return gear + 1
        else:
            # check if the RPM value of car is lower than the one suggested 
            # to shift down the gear from the current one
            if gear > 1 and rpm <= self.gearDown[gear-1]:
                return gear - 1
            else: # otherwhise keep current gear
                return gear
        
   
    # return True if the car is stuckCounter, otherwise false
    def stuck(self, cs):
        #check if car is currently stuckCounter
        print "abs(cs.getAngle()): ", abs(cs.getAngle()) , "   STUCKANGLE: ", STUCKANGLE
        
        #if ( abs(cs.getAngle()) > STUCKANGLE ):
        if( cs.getTrackPos() > 1.0 or cs.getTrackPos() < -1.0 ):
            #update stuckCounter counter
            self.stuckCounter = self.stuckCounter + 1
            self.bringingCartBack = 150
        else:
            if(self.bringingCartBack != 0):
                self.bringingCartBack = self.bringingCartBack - 1
            else:
                #if not stuckCounter reset stuckCounter counter
                self.stuckCounter = 0
        print "self.stuckCounter: ", self.stuckCounter
        return (self.stuckCounter > STUCKTIME)
    
    
    def bringCarBackOnTrack(self, cs):
        #set gear and steering command assuming car is 
        #pointing in a direction out of track
        
        #if car is pointing in direction of the street  
        if ( cs.getAngle()*cs.getTrackPos() > 0.0 ):
            gear = 1
            steer = cs.getAngle()/4
        # back of car is pointing into direction of street 
        else:
            #to bring car parallel to track axis
            steer = - cs.getAngle()/4 # steerLock; 
            gear = -1 # gear R
                    
        if self.bringingCartBack < 5:
            return CarControl(0,1.0,0,0,0,0,0)
                
        #Calculate clutching
        #clutching(cs,clutch);

        #build a CarControl variable and return it
        #CarControl cc (1.0,0.0,gear,steer,clutch)
        return CarControl(0.3,0.0,gear,steer,0,0,0)
