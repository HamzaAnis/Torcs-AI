''' 
Original implemnentation is from
http://code.activestate.com/recipes/577231/ (r1)
The recipe gives simple implementation of a Discrete Proportional-Integral-Derivative (PID) controller. PID controller gives output value for error between desired reference input and measurement feedback to minimize error value.
More information: http://en.wikipedia.org/wiki/PID_controller

cnr437@gmail.com

######    Example    #########
p=PID(3.0,0.4,1.2)
p.setPoint(5.0)
while True:
     pid = p.update(measurement_value)


This class was changed to be used in a TORCS driver
@author: Thomas Fischle
@contact: fisch27@gmx.de
'''

class PID:
    """
    Discrete PID control
    """

    def __init__(self, P=2.0, I=0.0, D=1.0, Derivator=0, Integrator=0, Integrator_max=500, Integrator_min=-500):

        self.Kp=P
        self.Ki=I
        self.Kd=D
        self.Derivator=Derivator
        self.Integrator=Integrator
        self.Integrator_max=Integrator_max
        self.Integrator_min=Integrator_min

        self._set_point=0.0
        self.error=0.0
        
        self.adapter = None

    def update(self,current_value):
        """
        Calculate PID output value for given reference input and feedback
        """

        self.error = self._set_point - current_value
        
        # get pid values from learning module
        if self.adapter:
            self.Kp, self.Ki, self.Kd= self.adapter.getPidParameters()

        self.P_value = self.Kp * self.error
        self.D_value = self.Kd * ( self.error - self.Derivator)
        self.Derivator = self.error

        self.Integrator = self.Integrator + self.error

        if self.Integrator > self.Integrator_max:
            self.Integrator = self.Integrator_max
        elif self.Integrator < self.Integrator_min:
            self.Integrator = self.Integrator_min

        self.I_value = self.Integrator * self.Ki

        PID = self.P_value + self.I_value + self.D_value
        
        #maximal steering -1 till +1
        if PID > 1.0:
            PID = 1.0
        if PID < -1.0:
            PID = -1.0

        return PID

    def setPoint(self,set_point):
        """
        Initilize the setpoint of PID
        """
        self._set_point = set_point


    def setIntegrator(self, Integrator):
        self.Integrator = Integrator

    def setDerivator(self, Derivator):
        self.Derivator = Derivator
        
    def setPid(self, KpKiKd):
        self.setKp(KpKiKd[0])
        self.setKi(KpKiKd[1])
        self.setKd(KpKiKd[2])

    def setKp(self,P):
        if P < 0.0:
            P = 0.0
        self.Kp=P
    
    def getKp(self):
        return self.Kp

    def setKi(self,I):
        if I < 0.0:
            I = 0.0
        self.Ki=I
        
    def getKi(self):
        return self.Ki

    def setKd(self,D):
        if D < 0.0:
            D = 0.0
        self.Kd=D
    
    def getKd(self):
        return self.Kd

    def getSetpoint(self):
        return self._set_point

    def getError(self):
        return self.error

    def getIntegrator(self):
        return self.Integrator

    def getDerivator(self):
        return self.Derivator
    
    def setAdapter(self, Adapter):
        self.adapter = Adapter
        self.adapter.initPlot()
        
## end of http://code.activestate.com/recipes/577231/ }}}
