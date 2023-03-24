# Code Contributions and Code Reviews

#### Focused Commits

Grade: Very Good

Feedback: There is a good amount of commits, and all commits affect only a small number of files. Most commits have a clear commit message, but [some](https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-33/-/commit/4259e6f630bd1a497abdf5fe5c19504d053221a2) can still be formulated more clearly. 


#### Isolation

Grade: Excellent

Feedback: It is good to see that you're using separate branches and merge requests for each feature. Additionally, your use of a "dev" branch guarantees that everything is effectively integrated prior to merging with the main branch.


#### Reviewability

Grade: Very Good

Feedback: Most commits have a clear title and description, that captures its purpose nicely. [This](https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-33/-/merge_requests/40) is one example of a really well done merge request with an extensive description. Other merge requests, like [this](https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-33/-/merge_requests/44/diffs) one, could have a bit more explanation. 


#### Code Reviews

Grade: Good

Feedback: Some merge requests, like [this one](https://gitlab.ewi.tudelft.nl/cse1105/2022-2023/teams/oopp-team-33/-/merge_requests/39), have received extensive constructive feedback. It is great to see that usually a large number of people approves merge requests, but some merge requests have no code reviews at all. Try to add a comment even if you don't necessarily see anything wrong in the code - you could add optimisation suggestions or highlight something that was elegantly done before approving the merge request.


#### Build Server

Grade: Very Good

Feedback: There are regular commits, and the build on main never fails because you make sure that everything is integrated correctly on the 'dev' branch. However, no checksyle rules have been added yet! Make sure to add these soon, since might have to rewrite already written code to fit with the checkstyle rules.

Overall, you did a great job at using various Git functionality like branches and merges. Keep up the descriptive titles and descriptions, and don't forget to add the checkstyle rules.

