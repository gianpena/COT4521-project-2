#show raw: box.with(fill: silver, radius: 2pt, inset: (x: 2pt), outset: (y: 2pt))

#set heading(numbering: "I.")
#set par(first-line-indent: 2em)

#align(center)[
  #text(weight: "bold", size: 24pt)[
    Computational Geometry\
    Second Project Conclusions
  ]

  \

  #text(size: 14pt)[
    #h(1fr) Gian Peña #h(2fr) Henrique Pitta Laranjinha #h(1fr)
  ]
]

\
\

= Challenges Faced
One significant challenge we faced was in turning a real map of Miami into polygons the program could use. The rules were at least five map elements, each one represented as a simple `Polygon`, with a description shown as the mouse hovers over it.

The way we ended up choosing to implement this was by tracing seven areas of the map by hand (Key Biscayne, Virginia Key, the FIU campus, Miami International Airport, Downtown, Coconut Grove, and Miami Beach) and writing down the vertices of each one in panel coordinates. Another issue we encountered was with the point-in-polygon test. There was some initial confusion on our part with the outputs we were seeing when the mouse was level with a vertex or a horizontal edge, because we had the edge rules from the slides in the wrong order.

#figure(
  image("demo.png"),
  caption: [ An image of the `GraphDisplay` class's code, along with the application's GUI open with the mouse over Key Biscayne. ],
  supplement: none
)
= What We Learned
We learned that locating a point on a map amounts to running the point-in-polygon test on each polygon until one of them contains the point, which is what `Algorithms.isPointInSet` does. To get the test right, horizontal edges are skipped and each edge counts its lower endpoint but not its upper one, so a vertex is never counted twice. We also learned how to write an event-driven program in Java, implementing `MouseMotionListener` so that every mouse movement updates the description and calls `repaint()`. Finally, we learned to draw a background image with `ImageIO` and to put a text area on top of it for the output.
