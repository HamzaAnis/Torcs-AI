'''
A very, very simple driver which is controlled by a PID-controller.
The parameters have definitely to be tuned for a stable driver!!  

Created on 03.05.2011

@author: MrFish
@contact: fisch27@gmx.de
'''

import SimplePythonClient.BaseDriver as BaseDriver
import SimplePythonClient.CarState as CarState
import SimplePythonClient.CarControl as CarControl
import SimplePythonClient.PID as PID

NUMBEROFRANGEFINDERSENSORS = 19

class Driver(BaseDriver.BaseDriver):

    def __init__(self, title):
        print "Driver init:", title
        self.steeringWheel = 0.0;
        self.pid = PID.PID( 0.60716028172, 0.000417343004059, 34.2653431279, 0, 0, 1.0, -1.0)
        
    def onShutdown(self):            
        print "Driver: Bye bye!"
        
        
    def getInitAngles(self):
        return [-90,-80,-70,-60,-50,-40,-30,-20,-10,0,10,20,30,40,50,60,70,80,90]
    
    
    def Update(self, buffer):
        cs = CarState.CarState(buffer)
        print "car state: ", str(cs)
        cc = self.__wDrive(cs)
        print "execute action: ", str(cc)
        return str(cc)
            
    
    #put the intelligence here    
    def __wDrive(self, currentCarState):      
        #chose action(s) 
        
        #print "currentCarState.getAngle():", currentCarState.getAngle()
        #Distance between the car and the track axis. The value is
        #normalized w.r.t to the track width: it is 0 when car is on
        #the axis, -1 when the car is on the right edge of the track
        #and +1 when it is on the left edge of the car. Values greater
        #than 1 or smaller than -1 mean that the car is outside of
        #the track.
        #    | +1    0    -1 |
        #    |               |
        #    |               |
        #    |               |
        #    |               |
        
        # steerAction > 0 left turn
        # steerAction < 0 right turn
        
        targetTrackPos = -0.5
        self.pid.setPoint(targetTrackPos)
        
        # errorTrackPos > 0 left turn
        # errorTrackPos < 0 right turn     
        self.steeringWheel = self.pid.update(currentCarState.getTrackPos())
            
        print 'steeringWheel = ',self.steeringWheel, '  p=',self.pid.getKp(),'  i=',self.pid.getKi(), '    d=', self.pid.getKd()
        
        #Speed        
        if currentCarState.getSpeedX() < 30.0:
            accel = 0.3
            gear = 1
        elif currentCarState.getSpeedX() < 90.0:
            accel = 0.4
            gear = 2
        else:
            gear = 2
            accel = 0.0        
                     
        action = CarControl.CarControl(accel, 0, gear, self.steeringWheel, 0, 0, 0)
        print "Action:", str(action)
        return action
     
 