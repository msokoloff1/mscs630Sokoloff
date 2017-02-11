import tensorflow as tf

class Network():
    def __init__(self, messageLength):
        self._inputMessage = tf.placeholder(tf.float32, [None,messageLength])


    def _convLayer1D(self, input, numOutputChannels, filterWidth, stride, name, pad = 'SAME',activation = tf.nn.sigmoid, bias = False):
        with tf.variable_scope(name) as scope:
            if(len(input) == 2):
                input = input.reshape(-1,int(input.get_shape[-1]),1)

            numInputChannels = int(input.get_shape()[-1])
            filter = self._weightVar((filterWidth, numInputChannels , numOutputChannels ) )
            conv = tf.nn.conv1(input, filter, stride = stride, padding = pad)
            if (bias):
                conv = conv + self._bias(numOutputChannels)
            return activation(conv)

    def _weightVar(self, shape):
        weights = tf.truncated_normal(shape, stddev=0.1)
        return tf.Variable(weights)

    def _fcLayer(self, input, numOutputs, name, bias = False, activation = tf.nn.sigmoid):
        with tf.variable_scope(name) as scope:
            weights = tf.truncated_normal((input.get_shape()[-1], numOutputs))
            result = tf.matmul(input, weights)
            if(bias):
                result = result + self._bias(numOutputs)

            return activation(result)

    def _bias(self, shape):
        bias = tf.constant(0.1, shape=shape)
        return tf.Variable(bias)

    def getInputTensor(self):
        return self._inputMessage

    def _combineKeyAndText(self, key, messageLength):
            concatenated = tf.concatenate(self._inputKey, self._inputMessage, axis=1)
            combinedInput = tf.gather(concatenated, [indexer(x) for x in range(messageLength)
                                                     for indexer in (lambda y: y, lambda y: y + messageLength)])
            return combinedInput



def getBobAliceLoss(bob, eve, alice, messageLength):
    conversationLoss = tf.abs(bob.output - alice._inputMessage)
    eveLoss = tf.abs(eve.output - alice._inputMessage)
    snoopLoss = tf.div(tf.pow( ((messageLength/2)-eveLoss),2), tf.pow((messageLength/2),2))
    totalLoss = conversationLoss + snoopLoss
    return totalLoss


def getEveLoss(eve, alice):
    eveLoss = tf.abs(eve.output - alice._inputMessage)
    return eveLoss




class Alice(Network):
    def __init__(self, messageLength, name):
        super().__init__(messageLength)
        self._inputKey = tf.placeholder(tf.float32, [None, messageLength])
        combinedInput = self._combinKeyAndText(self._inputKey, messageLength)
        with tf.variable_scope(name) as scope:
            fc1 = self._fcLayer(combinedInput, messageLength * 2, 'a_fc1')
            conv1 = self._convLayer1D(fc1,   numOutputChannels=2, filterWidth=4, stride=1, name='a_conv1')
            conv2 = self._convLayer1D(conv1, numOutputChannels=4, filterWidth=2, stride=2, name='a_conv2')
            conv3 = self._convLayer1D(conv2, numOutputChannels=4, filterWidth=1, stride=1, name='a_conv3')
            conv4 = self._convLayer1D(conv3, numOutputChannels=1, filterWidth=1, stride=1, name='a_conv4', activation = tf.nn.tanh)
            self.output = conv4



class Bob(Network):
    def __init__(self, messageLength, alice,  name):
        super().__init__(messageLength)
        self._inputMessage = alice.output
        self._inputKey = tf.placeholder(tf.float32, [None, messageLength])
        combinedInput = self._combinKeyAndText(self._inputKey, messageLength)
        with tf.variable_scope(name) as scope:
            fc1 = self._fcLayer(combinedInput, messageLength * 2,'b_fc1')
            conv1 = self._convLayer1D(fc1,   numOutputChannels=2, filterWidth=4, stride=1, name='b_conv1')
            conv2 = self._convLayer1D(conv1, numOutputChannels=4, filterWidth=2, stride=2, name='b_conv2')
            conv3 = self._convLayer1D(conv2, numOutputChannels=4, filterWidth=1, stride=1, name='b_conv3')
            conv4 = self._convLayer1D(conv3, numOutputChannels=1, filterWidth=1, stride=1, name='b_conv4', activation = tf.nn.tanh)
            self.output = conv4


class Eve(Network):
    def __init__(self, messageLength, alice, name):
        super().__init__(messageLength)
        self._inputMessage = alice.output
        with tf.variable_scope(name) as scope:
            fc1 = self._fcLayer(self._inputMessage, messageLength, 'e_fc1')
            conv1 = self._convLayer1D(fc1, numOutputChannels=2, filterWidth=4, stride=1, name='e_conv1')
            conv2 = self._convLayer1D(conv1, numOutputChannels=4, filterWidth=2, stride=2, name='e_conv2')
            conv3 = self._convLayer1D(conv2, numOutputChannels=4, filterWidth=1, stride=1, name='e_conv3')
            conv4 = self._convLayer1D(conv3, numOutputChannels=1, filterWidth=1, stride=1, name='e_conv4', activation = tf.nn.tanh)
            self.output = conv4
