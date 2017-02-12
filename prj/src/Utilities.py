import tensorflow as tf
import threading
import numpy as np




def ensureRank2(input):
    if (len(input.get_shape()) == 3):
        input = tf.reshape(input, ([-1, int(input.get_shape()[1])]))

    tf.assert_rank(input, 2, message="Tensor is not rank 2")
    return input

def ensureRank3(input):
    if (len(input.get_shape()) == 2):
        input = tf.expand_dims(input, 2)

    tf.assert_rank(input, 3, message="Tensor is not rank 3")
    return input


def getBobAliceLoss(bob, eve, alice, messageLength):
    bobOutput = ensureRank2(bob.output)
    eveOutput = ensureRank2(eve.output)

    conversationLoss = tf.abs(bobOutput - alice._inputMessage)
    eveLoss = tf.reduce_mean(tf.abs(eveOutput - alice._inputMessage))
    snoopLoss = tf.reduce_mean(tf.div(tf.pow( ((messageLength/2)-eveLoss),2), tf.pow((messageLength/2),2)))
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
    getBitSequence = lambda numBits, BatchSize: queue.insert(0, np.random.randint(2, size=(batchSize, numBits)))
    for _ in range(100) : getBitSequence(numBits, batchSize)
    while True:
        for _ in range(2) : threading.Thread(target=getBitSequence, args=(numBits,batchSize,)).start()
        yield {'key' : queue.pop(), 'plainText' : queue.pop()}
