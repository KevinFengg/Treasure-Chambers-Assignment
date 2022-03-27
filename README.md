# ASSIGNMENT-3---Treasure-Chambers

Completed on Marth 14th, 2022.

This project provides insight to the following:
  - Doubly linked lists
  - Implementing a stack of doubly linked lists
  - Finding a path using a stack

Given a map of chambers (implemented as nodes to a doubly linked list), a path to all treasure chambers is found and returned as a stack. This may not be the shortest path as the program is designed to traverse through the chamber of smallest index and backtrack until an alternative path for a previous node is found. As the nodes of the map are being traversed, they are set as "marked" so signify that the path includes those specific nodes. Also, nodes that lead to a dead end are set as "popped" (as in popped from the doubly linked list). This way, the program can properly distinguish which paths lead to dead ends and change the doubly linked list of chambers included in the path accordingly.

NOTE: Only the doubly linked list stack interface (DLStack.java) and the program responsible for finding a path (FindPath.java) were implemented by me.
