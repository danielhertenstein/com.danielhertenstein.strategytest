# Strategy Pattern Test Using Solar Panels

[![Build Status](https://travis-ci.org/danielhertenstein/com.danielhertenstein.strategytest.svg?branch=master)](https://travis-ci.org/danielhertenstein/com.danielhertenstein.strategytest)

This is a simple demonstration of the Strategy Design Pattern using solar
panels as different strategies. There is a House class that can have some type
of power generating object. The house doesn't care what the object is as long
as it implements the PowerGenerator interface. There are then multiple types
of solar panels that implement the interface and Houses can switch between
them.