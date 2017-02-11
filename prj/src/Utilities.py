import tensorflow as tf
import threading
import numpy as np




def getBobAliceLoss(bob, eve, alice, messageLength):
    conversationLoss = tf.abs(bob.output - alice._inputMessage)
    eveLoss = tf.abs(eve.output - alice._inputMessage)
    snoopLoss = tf.div(tf.pow( ((messageLength/2)-eveLoss),2), tf.pow((messageLength/2),2))
    totalLoss = conversationLoss + snoopLoss
    return totalLoss




def getEveLoss(eve, alice):
    eveLoss = tf.abs(eve.output - alice._inputMessage)
    return eveLoss




def getTurn(aliceUpdateOp, bobUpdateOp, eveUpdateOp):
    while True:
        yield [aliceUpdateOp, bobUpdateOp]
        yield [eveUpdateOp] #<- update eve twice
        yield [eveUpdateOp]




def getData(numBits, batchSize):
    queue = []
    getBitSequence = lambda numBits, BatchSize: queue.insert(0,np.random.randint(2, size=(batchSize, numBits)))
    for _ in range(2) : getBitSequence(numBits, batchSize)
    while True:
        for _ in range(2) : threading.Thread(target=getBitSequence(), args=(numBits,batchSize,)).start()
        yield {'key' : queue.pop(), 'plainText' : queue.pop()}