'''
This class is based on the C++ code of Daniele Loiacono

Created on 05.05.2011
@author: Thomas Fischle
@contact: fisch27@gmx.de
'''
class SimpleParser(object):
    
    def __init__(self):
        pass
    
    def stringify(self, tag, value):
    
        STR = "("
        STR = STR + str(tag)
        
        #if value is a list iterate through the list
        if type(value)==type(list()):
            for v in value:
                STR = STR + " " + str(v)
        else:
            STR = STR + " " + str(value)
        
        STR = STR + ")";
        return STR
    
    def parse(self, sensors, tag):
        #values to return
        values = []
        
        #remove first and last bracket
        sensors =  sensors[1:len(sensors)-1]
        
        #split in list containing strings between ( )
        listSensors = sensors.split(")(")
        
        for l in listSensors:
            entry = l.split(" ")
            
            if entry[0]==tag:
                #remove tag
                del entry[0]
                
                #create list of values
                for v in entry:
                    values.append( float(v) )
                
        return values

    