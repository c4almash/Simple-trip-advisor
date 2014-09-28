Test Driven Development
=======================
Summary
-------
1. Meetings
	- 4 physical
	- 1 on-line
2. Work Assignment
	- Stage One: In physical meetings
	- Stage Two: On Github
	- Stage Three[conditional]: Team collaboration of resources to resolve a block [using an XP technique]
3. Communication Mediums
	- Day-to-day on Facebook [group IM]
	- On-line meetings on Skype
4. Design Process  
	a) Process of choice: Test Driven development  
	b) Initial failing test  
	c) skeleton code to deal with simple test: constructors, setters and getters.  
	d) Continuous development using our work assignment strategy.  
	e) Reaching our minimum viable product and detailing our final adjustments/amendments.  

Communication
--------------
In our first meeting and we made a Facebook instant messaging group. Instant messaging allowed us to finalize the rest of our communication tools, we agreed on using Github issue ticket management and to use Skype for on-line meetings.

Schedule
--------
Our schedule main objective was to have an MVP by the 22nd of September. To stay in line with that target, we decided on having four physical meetings and one on-line.Three physical meeting were scheduled after class and one after reaching an MVP. Team members individual assignments did not have a strict schedule but depended more the difficulty/complexity of the task on hand.

Tasks Assignment
----------------
During our in class meetings, all team members would share their strengths and weaknesses and accordingly be assigned tasks. At our meetings we would have a write-up of issues, which we then open Github tickets for. We used Github continuously throughout the project for day-to-day tasks and overall design issues.

GitHub Issue Management System
------------------------------
Outside meetings we would group failing test cases into issues[new tickets] on GitHub and team
members were to assign themselves to issues they are willing/able to fix. Issues that are harder to resolve, we used an XP technique were two team members tackle and resolve the ticket together. This happened whilst updating the entire group on the Facebook IM.

Strategic Issue
---------------
The issue of using a double or long [data type] for price of ticket came up in our last meeting. One of the team members was concerned that if our implementation didn't output an accurate enough [not rounded] price of tickets we could potentially fail some JUnit test cases. Our initial implementation was a long but after a we discussed the design aspect we came to realize that a double is more appropriate. This is due to the fact that the price is more likely to be a decimal than a large integer.  

Insight in to the Development Process
-------------------------------------
Our initial code development stage included making the getters and setters along with constructor
to get some of the initial tests passing. We've then started to write additional failing test cases in accordance
with TDD principles.

Important Decisions
-------------------
We agreed to do day-to-day commits in our personal repo [a Team repo fork] then only do a pull request with the master after getting the entire team's approval. This way Github doesn't get clouded with too many commits and the workflow is more fluid.
